using Xamarin.Forms;

namespace myForum
{
    public partial class App : Application
    {
        //Log status 
        internal static bool IsUserLoggedIn;

        public App()
        {
            InitializeComponent();

            //Set up the main screen page
            MainPage = new NaviationTab();

        }

        protected override void OnStart()
        {
            // Handle when your app starts
        }

        protected override void OnSleep()
        {
            // Handle when your app sleeps
        }

        protected override void OnResume()
        {
            // Handle when your app resumes
        }
    }
}
