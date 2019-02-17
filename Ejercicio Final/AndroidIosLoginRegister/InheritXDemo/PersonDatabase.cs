using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Threading.Tasks;
using SQLite;

namespace InheritXDemo
{
	public class PersonDatabase
	{

		readonly SQLiteAsyncConnection database;

		public PersonDatabase(String databasePath)
		{
			database = new SQLiteAsyncConnection(databasePath);
			database.CreateTableAsync<DatabaseModel>().Wait();

		}

		//This function will save data into database
		public int SaveDataIntoDatabase(DatabaseModel mDatabaseModel)
		{
			if (!isUserExists(mDatabaseModel.personMobileNumber))
			{
				var data = database.InsertAsync(mDatabaseModel).Result;
				Debug.WriteLine("SaveDataIntoDatabase >> " + data.ToString());
				return 1;
			}
			else
			{
				return 0;
			}
		}

		//This function will update data into database.
		public Task<int> UpdateDataIntoDatabase(DatabaseModel mDatabaseModel)
		{
			return database.UpdateAsync(mDatabaseModel);
		}

		//This function will fetch all the person listing.
		public Task<List<DatabaseModel>> GetPersonListing()
		{
			return database.Table<DatabaseModel>().ToListAsync();
		}

		//This function will check login credential from database
		public Boolean isUserAuthenticated(String strMobileNumber, String strPassword)
		{
			Boolean isAuthenticated = false;

			var mDatabaseModel = database.Table<DatabaseModel>().Where(i => i.personMobileNumber == strMobileNumber)
										 .FirstOrDefaultAsync().Result;

			if (mDatabaseModel != null)
			{
				if (mDatabaseModel.personMobileNumber.Equals(strMobileNumber) && mDatabaseModel.personPassword.ToLower()
					.Equals(strPassword.ToLower()))
				{
					isAuthenticated = true;
				}
			}

			return isAuthenticated;
		}

		//This function will check if user exists or not when creating new user
		public Boolean isUserExists(String strMobileNumber)
		{
			Boolean isExists = false;

			var mData = database.Table<DatabaseModel>().Where(i => i.personMobileNumber.Equals(strMobileNumber))
								.FirstOrDefaultAsync().Result;

			if (mData != null)
			{
				isExists = true;
			}
			else
			{
				isExists = false;
			}


			return isExists;
		}

		public Boolean updatePassword(string strMobileNumber, string strPassword)
		{
			var mData = database.Table<DatabaseModel>().Where(i => i.personMobileNumber.Equals(strMobileNumber))
											.FirstOrDefaultAsync().Result;

			if (mData != null)
			{

				DatabaseModel mDatabaseModel = new DatabaseModel();
				mDatabaseModel.id = mData.id;
				mDatabaseModel.personEmail = mData.personEmail;
				mDatabaseModel.personPassword = strPassword;
				mDatabaseModel.personMobileNumber = mData.personMobileNumber;

				database.UpdateAsync(mDatabaseModel);

				return true;
			}
			else
			{
				return false;
			}


		}

		public Task<DatabaseModel> getUserData(String strMobileNumber)
		{
			return database.Table<DatabaseModel>().Where(i => i.personMobileNumber.Equals(strMobileNumber))
														.FirstOrDefaultAsync();
		}


	}
}

