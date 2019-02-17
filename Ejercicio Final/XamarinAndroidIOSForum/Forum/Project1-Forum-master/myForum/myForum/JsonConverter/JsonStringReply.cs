using System;

using Xamarin.Forms;
using Newtonsoft.Json;
using System.Collections.Generic;

namespace myForum
{
    public class JsonStringReply
    {
		//Encode to Json
        public string ToJsonString(Reply reply)
		{
			return JsonConvert.SerializeObject(reply);
		}
		//Decode to object
		public Reply ToObject(String json)
		{
			Reply reply = JsonConvert.DeserializeObject<Reply>(json);
			return reply;
		}
		//Decode from Json to list
        public static List<Reply> ToList(String json)
		{
			List<Reply> list = JsonConvert.DeserializeObject<List<Reply>>(json);
			return list;
		}
		//Encode from List to Json 
		public String ListToJson(List<Reply> reply)
		{
			String json = JsonConvert.SerializeObject(reply);
			return json;
		}
    }
}

