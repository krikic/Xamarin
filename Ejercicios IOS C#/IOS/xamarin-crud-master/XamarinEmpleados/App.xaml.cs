using Xamarin.Forms;

namespace XamarinEmpleados
{
    public partial class App : Application
    {
        public App()
        {
            MainPage = new NavigationPage(new PaginadeInicio());
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
