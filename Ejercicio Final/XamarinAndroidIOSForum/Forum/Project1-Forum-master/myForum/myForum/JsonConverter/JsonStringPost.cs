using System;

using Newtonsoft.Json;
using System.Collections.Generic;

namespace myForum
{
    public class JsonStringPost
    {
		//Encode to Json
		public string ToJsonString(Post post)
		{
			return JsonConvert.SerializeObject(post);
		}
		//Decode to object
		public Post ToObject(String json)
		{
			Post post = JsonConvert.DeserializeObject<Post>(json);
			return post;
		}
		//Decode from Json to list
		public List<Post> ToList(String json)
		{
			List<Post> list = JsonConvert.DeserializeObject<List<Post>>(json);
			return list;
		}
		//Encode from List to Json 
		public String ListToJson(List<Post> post)
		{
			String json = JsonConvert.SerializeObject(post);
			return json;
		}
    }
}

