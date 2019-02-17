using Xamarin.Forms;

namespace InheritXDemo
{
	public partial class App : Application
	{

		public static App Instance;
		public static PersonDatabase mPersonDatabase;

		public App()
		{
			InitializeComponent();
			Instance = this;
			MainPage = new NavigationPage(new NavigationDrawerDetailForm());
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

		public void ClearNavigationAndGoToPage(MasterDetailPage page)
		{
			MainPage = new NavigationPage(page);

		}

		public static PersonDatabase Database
		{
			get{
				
				if (mPersonDatabase == null)
				{
					mPersonDatabase = new PersonDatabase(DependencyService.Get<iLocalFileHelper>().GetLocalFilePath("InheritX_Database.db3"));
				}
				return mPersonDatabase;

			}
		}

		public void GoToLoginScreen(ContentPage contentPage)
		{
			MainPage = new NavigationPage(new LoginForm());
		}
	}
}
