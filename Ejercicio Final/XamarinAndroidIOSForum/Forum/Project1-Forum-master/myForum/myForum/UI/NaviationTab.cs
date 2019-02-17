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
            //Set tab bar image
            topic.Icon = "topic.png";
            profile.Icon = "profile.png";
		  
            //Set the navigation bar title
			topic.Title = "Topics";
            profile.Title = "Profile";

            //Add to view
			Children.Add(topic);
            Children.Add(profile);  
        }
    }
}

