using System;

using Xamarin.Forms;

namespace myForum
{
    public class Reply
    {
		public string UserReply { get; set; }
		public string Username { get; set; }
		public string Time { get; set; }

		public Reply(string reply, string username, string time)
		{
			this.UserReply = reply;
			this.Username = username;
			this.Time = time;
		}
    }
}

