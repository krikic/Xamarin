using System;

using Newtonsoft.Json;
using System.Collections.Generic;

namespace myForum
{
	public class JsonUserPost
	{
		//Encode to Json
        public string ToJsonString(UserPost post)
		{
			return JsonConvert.SerializeObject(post);
		}
		//Decode to object
		public UserPost ToObject(String json)
		{
			UserPost post = JsonConvert.DeserializeObject<UserPost>(json);
			return post;
		}
		//Decode from Json to list
		public List<UserPost> ToList(String json)
		{
			List<UserPost> list = JsonConvert.DeserializeObject<List<UserPost>>(json);
			return list;
		}
		//Encode from List to Json 
		public String ListToJson(List<UserPost> post)
		{
			String json = JsonConvert.SerializeObject(post);
			return json;
		}
	}
}



