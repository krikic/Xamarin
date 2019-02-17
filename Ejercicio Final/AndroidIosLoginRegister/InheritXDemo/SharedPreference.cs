using System;
using Plugin.Settings;
using Plugin.Settings.Abstractions;

namespace InheritXDemo
{
	public class SharedPreference
	{

		private const String MobileNumberKey = "_mobileNumber";
		private static readonly String MobileNumberDefault = String.Empty;
		private const String IsLoggedInKey = "_isLoggedIn";
		private static readonly String IsLoggedInDefault = "false";
		private const String UserName = "_userName";
		private static readonly String UserNameDefault = String.Empty;
		private const String EmailKey = "_email";
		private static readonly String EmailDefault = String.Empty;

		private static ISettings AppSettings
		{
			get
			{
				return CrossSettings.Current;
			}
		}

		public static String GetMobileNumber
		{
			get { return AppSettings.GetValueOrDefault<string>(MobileNumberKey, MobileNumberDefault);}
			set {AppSettings.AddOrUpdateValue<string>(MobileNumberKey,value); }
		}

		public static String GetLoginStatus
		{
			get { return AppSettings.GetValueOrDefault<String>(IsLoggedInKey,IsLoggedInDefault); }
			set { AppSettings.AddOrUpdateValue<String>(IsLoggedInKey, value);}
		}

		public static String GetUsername
		{
			get {return AppSettings.GetValueOrDefault<String>(UserName,UserNameDefault); }
			set {AppSettings.AddOrUpdateValue<String>(UserName,value); }
		}

		public static String GetEmailAddress
		{
			get {return AppSettings.GetValueOrDefault<String>(EmailKey,EmailDefault); }
			set {AppSettings.AddOrUpdateValue<String>(EmailKey,value); }
		}
	}
}

 
