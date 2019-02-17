using System;
using System.Threading.Tasks;
using System.Windows.Input;
using Xamarin.Forms;

namespace NavigationTransitions
{
    public class ThirdPageModel
    {
      
        public ICommand NavigateBack { get; private set; }
     

        public ThirdPageModel()
        {
            var mainPage = Application.Current.MainPage;
            var sharedLock = new SharedLock();

           
            NavigateBack = new SingleLockCommand(() => Task.FromResult(mainPage.SendBackButtonPressed()), sharedLock);
            
        }
    }
}
