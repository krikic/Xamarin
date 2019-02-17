using System;

using Xamarin.Forms;
using Newtonsoft.Json;

namespace myForum
{
    public class JsonStringUser
    {
		//Encode to Json
		public string ToJsonString(User user)
		{
			return JsonConvert.SerializeObject(user);
		}
		//Decode to object
        public User ToObject(String json)
		{
			User user = JsonConvert.DeserializeObject<User>(json);
			return user;
		}
	}
}

