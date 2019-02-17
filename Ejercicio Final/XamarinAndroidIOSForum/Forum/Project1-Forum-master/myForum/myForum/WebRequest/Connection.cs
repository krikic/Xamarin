using System;

using System.Net;
using System.IO;
using System.Threading.Tasks;
using System.Diagnostics;

namespace myForum
{
    public class Connection
    {
        //Initial URL for web request
		private static string HTTPServer = "http://introtoapps.com/datastore.php?appid=215197324";

		/*-------------------------------------START---WEB--REQUEST-----------------------------------------*/

		//Get list 
		public static async Task<string> GetList()
		{
			try
			{
				string action = HTTPServer + "&action=list";
				Uri uri = new Uri(action);
				WebRequest request = WebRequest.Create(uri);
				request.Method = "POST";

			    return await ServerResponse(request);
			}
			catch (Exception e)
			{
				Debug.WriteLine(e);

				return null;
			}
		}

		//Create user
		public static async Task<string> CreateUser(string username, string json)
		{
			try
			{
				//Encode the json to the url
				json = WebUtility.UrlEncode(json);
				//The request url
				string action = HTTPServer + "&action=save&objectid=" + username + ".user" + "&data=" + json;
				Uri uri = new Uri(action);
				WebRequest request = WebRequest.Create(uri);
				request.Method = "POST";

				return await ServerResponse(request);
			}
			catch (Exception e)
			{
				Debug.WriteLine(e);

                return null;
			}
		}

		//Retrieve username and password
		public static async Task<string> LoadUser(string username)
		{
			try
			{
				string action = HTTPServer + "&action=load&objectid=" + username + ".user";
				Uri uri = new Uri(action);
				WebRequest request = WebRequest.Create(uri);
				request.Method = "POST";


			    return await ServerResponse(request);
			}
			catch (Exception e)
			{
				Debug.WriteLine(e);
				return null;
			}
		}

		//Post the new topic
		public async void NewPost(string topic, string json)
		{
			try
			{
                string action = HTTPServer + "&action=append&objectid="+ topic + ".topic" + "&data=" + json;
				Uri uri = new Uri(action);
				WebRequest request = WebRequest.Create(uri);
				request.Method = "POST";


				await ServerResponse(request);
			}
			catch (Exception e)
			{
				Debug.WriteLine(e);
			}
		}

		//User post by per user
		public async void UserPost(string username, string json)
		{
			try
			{
				string action = HTTPServer + "&action=append&objectid=" + username + ".post" + "&data=" + json;
				Uri uri = new Uri(action);
				WebRequest request = WebRequest.Create(uri);
				request.Method = "POST";


				await ServerResponse(request);
			}
			catch (Exception e)
			{
				Debug.WriteLine(e);
			}
		}
		//User reply
		public  async Task<string> LoadReply(int id)
		{
			try
			{
				string action = HTTPServer + "&action=load&objectid=" + id + ".reply";
				Uri uri = new Uri(action);
				WebRequest request = WebRequest.Create(uri);
				request.Method = "POST";


				return await ServerResponse(request);
			}
			catch (Exception e)
			{
				Debug.WriteLine(e);
				return null;
			}
		}

		//User reply
        public async Task<string> SendReply(int id, string reply)
		{
			try
			{
				string action = HTTPServer + "&action=append&objectid=" + id + ".reply" + "&data=" + reply;
				Uri uri = new Uri(action);
				WebRequest request = WebRequest.Create(uri);
				request.Method = "POST";


				return await ServerResponse(request);
			}
			catch (Exception e)
			{
				Debug.WriteLine(e);
				return null;
			}
		}

		//Retrieve the post by per user from cloud
		public async Task<string> LoadUserPost(string username)
		{
			try
			{
                string action = HTTPServer + "&action=load&objectid=" + username + ".post";
				Uri uri = new Uri(action);
				WebRequest request = WebRequest.Create(uri);
				request.Method = "POST";

				return await ServerResponse(request);
			}
			catch (Exception e)
			{
				Debug.WriteLine(e);
				return null;
			}
		}

		//Retrieve post from cloud
		public async Task<string> LoadPost(string topic)
		{
			try
			{
				string action = HTTPServer + "&action=load&objectid="+ topic +".topic";
				Uri uri = new Uri(action);
				WebRequest request = WebRequest.Create(uri);
				request.Method = "POST";

				return await ServerResponse(request);
			}
			catch (Exception e)
			{
				Debug.WriteLine(e);
				return null;
			}
		}


		//Get response from server
		public static async Task<string> ServerResponse(WebRequest request)
		{
			string result = "";

			// Send the request to the server and wait for the response:
			using (WebResponse response = await request.GetResponseAsync())
			{
				// Get a stream representation of the HTTP web response:
				using (Stream stream = response.GetResponseStream())
				{
					StreamReader objStream = new StreamReader(stream);

					string sLine = "";
					while (sLine != null)
					{
						sLine = objStream.ReadLine();
						if (sLine != null)
							result += sLine + "\n";
					}
				}
			}
			return result;
		}
     /*-------------------------------------END---WEB--REQUEST--------------------------------------------*/
    }
}

