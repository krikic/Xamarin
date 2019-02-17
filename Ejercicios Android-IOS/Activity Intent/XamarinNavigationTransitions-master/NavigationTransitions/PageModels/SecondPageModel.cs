using System;
using System.Threading.Tasks;
using System.Windows.Input;
using Xamarin.Forms;

namespace NavigationTransitions
{
    public class SecondPageModel
    {
      
        public ICommand NavigateBack { get; private set; }

        public SecondPageModel()
        {
            var mainPage = Application.Current.MainPage;
            var sharedLock = new SharedLock();

            NavigateBack = new SingleLockCommand(() => Task.FromResult(mainPage.SendBackButtonPressed()), sharedLock);
        }
    }
}
