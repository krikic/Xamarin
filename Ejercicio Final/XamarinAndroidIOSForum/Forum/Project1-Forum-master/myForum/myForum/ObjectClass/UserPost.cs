using System;

namespace myForum
{
	public class UserPost
	{
        public string Topic { get; set; }
		public string Text { get; set; }
		public string Description { get; set; }
		public string Username { get; set; }
        public string Time { get; set; }

        public UserPost(string topic, string text, string description, string username, string time)
		{
            this.Topic = topic;
			this.Text = text;
			this.Description = description;
			this.Username = username;
            this.Time = time;
		}
	}
}


