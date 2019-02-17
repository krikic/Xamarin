﻿using Xamarin.UITest;

using InvestmentDataSampleApp.Shared;

using Query = System.Func<Xamarin.UITest.Queries.AppQuery, Xamarin.UITest.Queries.AppQuery>;

namespace InvestmentDataSampleApp.UITests
{
	public class AddOpportunityPage : BasePage
	{
		#region Constant Fields
		const int _entryCellXOffset = 200;

		readonly Query TopicEntry;
		readonly Query CompanyEntry;
		readonly Query LeaseAmountEntry;
		readonly Query OwnerEntry;
		readonly Query DBAEntry;

		readonly Query SaveButton;
		readonly Query CancelButton;
		#endregion

		#region Construvtors
		public AddOpportunityPage(IApp app, Platform platform) : base(app, platform)
		{
			TopicEntry = x => x.Marked(AutomationIdConstants.TopicEntry);
			CompanyEntry = x => x.Marked(AutomationIdConstants.CompanyEntry);
			LeaseAmountEntry = x => x.Marked(AutomationIdConstants.LeaseAmountEntry);
			OwnerEntry = x => x.Marked(AutomationIdConstants.OwnerEntry);
			DBAEntry = x => x.Marked(AutomationIdConstants.DBAEntry);

			SaveButton = x => x.Marked(AutomationIdConstants.SaveButton);
			CancelButton = x => x.Marked(AutomationIdConstants.CancelButton);
		}
		#endregion

		#region Properties
		public bool IsErrorMessageDisplayed => app.Query("OK").Length > 0;
		#endregion

		#region Methods
		public void PopulateAllFields(string topicText, string companyText, int leaseAmount, string ownerText, string dbaText, bool shouldUseKeyboardReturnButton)
		{
			if (shouldUseKeyboardReturnButton)
				PopulateAllFieldsByUsingKeyboardReturnButton(topicText, companyText, leaseAmount, ownerText, dbaText);
			else
				PopulateAllFieldsByTappingEachEntry(topicText, companyText, leaseAmount, ownerText, dbaText);
		}

		public void TapSaveButton()
		{
			Query saveButtonQuery;

			if (OniOS)
				saveButtonQuery = SaveButton;
			else
				saveButtonQuery = x => x.Marked("Save");

			app.WaitForElement(saveButtonQuery);
			app.Tap(saveButtonQuery);

			app.Screenshot("Tapped Save Button");
		}

		public void TapCancelButton()
		{
			if (OniOS)
				app.Tap(CancelButton);
			else
				app.Tap(x => x.Marked("Cancel"));

			app.Screenshot("Tapped Cancel Button");
		}

		void PopulateAllFieldsByTappingEachEntry(string topicText, string companyText, int leaseAmount, string ownerText, string dbaText)
		{
			EnterTextThenDismissKeyboard(TopicEntry, topicText);
			EnterTextThenDismissKeyboard(CompanyEntry, companyText);
			EnterTextThenDismissKeyboard(LeaseAmountEntry, leaseAmount.ToString());
			EnterTextThenDismissKeyboard(OwnerEntry, ownerText);
			EnterTextThenDismissKeyboard(DBAEntry, dbaText);
		}

		void PopulateAllFieldsByUsingKeyboardReturnButton(string topicText, string companyText, int leaseAmount, string ownerText, string dbaText)
		{
			EnterTextThenPressEnter(TopicEntry, topicText);
			EnterTextThenPressEnter(CompanyEntry, companyText);
			EnterTextThenPressEnter(LeaseAmountEntry, leaseAmount.ToString());
			EnterTextThenPressEnter(OwnerEntry, ownerText);
			EnterTextThenPressEnter(DBAEntry, dbaText);
		}

		void EnterTextThenDismissKeyboard(Query entryQuery, string text)
		{
			app.EnterText(entryQuery, text);
			app.DismissKeyboard();

			app.Screenshot($"Entered {text} into {nameof(entryQuery)}");
		}

		void EnterTextThenPressEnter(Query entryQuery, string text)
		{
			app.EnterText(entryQuery, text);
			app.PressEnter();

            app.Screenshot($"Entered {text}");
		}
		#endregion
	}
}
