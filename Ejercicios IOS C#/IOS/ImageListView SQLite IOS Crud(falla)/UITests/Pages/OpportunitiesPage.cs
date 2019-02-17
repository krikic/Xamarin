﻿using System;

using Xamarin.UITest;

using InvestmentDataSampleApp.Shared;

using Query = System.Func<Xamarin.UITest.Queries.AppQuery, Xamarin.UITest.Queries.AppQuery>;

namespace InvestmentDataSampleApp.UITests
{
	public class OpportunitiesPage : BasePage
	{
		#region Constant Fields
		readonly Query AddOpportunityButton;
		readonly Query OpportunitySearchBar;
		readonly Query WelcomeViewOkButton;
		#endregion

		#region Constructors
		public OpportunitiesPage(IApp app, Platform platform) : base(app, platform)
		{
			AddOpportunityButton = x => x.Marked(AutomationIdConstants.AddOpportunityButton);
			OpportunitySearchBar = x => x.Marked(AutomationIdConstants.OpportunitySearchBar);
			WelcomeViewOkButton = x => x.Marked(AutomationIdConstants.WelcomeViewOkButton);
		}
		#endregion

		#region Properties
		public bool IsWelcomeViewVisible => IsWelcomeViewOnScreen();
		#endregion

		#region Methods
		public void TapAddOpportunityButton()
		{
			if (OniOS)
				app.Tap(AddOpportunityButton);
			else
				app.Tap(x => x.Class("ActionMenuItemView"));
			app.Screenshot("Tapped Add Opportunity Button");
		}

		public void TapOpportunityViewCell(string topic)
		{
			app.ScrollDownTo(topic);
			app.Tap(topic);
			app.Screenshot($"Tapped ${topic} View Cell");
		}

		public void DeleteViewCell(string topic)
		{
			app.ScrollDownTo(topic);

			if (OniOS)
				app.SwipeRightToLeft(topic);
			else
				app.TouchAndHold(topic);

			app.Tap("Delete");
		}

		public void TapWelcomeViewOkButton()
		{
			app.Tap(WelcomeViewOkButton);
			app.Screenshot("Welcome View Ok Button Tapped");
		}

		public void Search(string searchString)
		{
			app.Tap(OpportunitySearchBar);
			app.EnterText(searchString);
			app.DismissKeyboard();
			app.Screenshot($"Entered {searchString} into Search Bar");
		}

		public bool DoesViewCellExist(string topic, int timeoutInSeconds = 10)
		{
			try
			{
				app.ScrollDownTo(topic, timeout: TimeSpan.FromSeconds(timeoutInSeconds));
			}
			catch (Exception)
			{
				return false;
			}

			return true;
		}

		bool IsWelcomeViewOnScreen(int timeoutInSeconds = 10)
		{
			try
			{
				app.WaitForElement(WelcomeViewOkButton, timeout: TimeSpan.FromSeconds(timeoutInSeconds));
			}
			catch
			{
				return false;
			}

			return true;
		}
		#endregion
	}
}
