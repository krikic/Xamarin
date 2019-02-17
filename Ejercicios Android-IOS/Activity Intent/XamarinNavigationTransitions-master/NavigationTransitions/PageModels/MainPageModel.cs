using System;
using System.Windows.Input;
using Xamarin.Forms;

namespace NavigationTransitions
{
    public class MainPageModel
    {
        public ICommand NavigateToSecondPage { get; private set; }
        public ICommand NavigateToThirdPage { get; private set; }

        public MainPageModel()
        {
			var sharedLock = new SharedLock();
            NavigateToSecondPage = new SingleLockCommand(() => Application.Current.MainPage.Navigation.PushAsync(new SecondPage())); //Lazy, Mainpage does not exist in this ctor.
             NavigateToThirdPage = new SingleLockCommand(() => Application.Current.MainPage.Navigation.PushAsync(new ThirdPage()));
        }
    }
}
