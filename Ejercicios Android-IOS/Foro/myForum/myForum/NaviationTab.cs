using System;

using Xamarin.Forms;

namespace myForum
{
    public class NaviationTab : TabbedPage
    {
        public NaviationTab()
        {
            //Set the navigation bar
            NavigationPage topic = new NavigationPage(new TopicSystem());
            NavigationPage profile = new NavigationPage(new ProfileSystem());
			//navigationPage.Icon = "schedule.png";

            //Set the navigation bar title
			topic.Title = "Topics";
            profile.Title = "Profile";

            //Add to view
			Children.Add(topic);
            Children.Add(profile);  
        }
    }
}

