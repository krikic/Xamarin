//
// Copyright (c) 2016, MindFusion LLC - Bulgaria.
//

using System;
using System.Collections.Generic;
using Xamarin.Forms;

using MindFusion.Drawing;
using MindFusion.Scheduling;


namespace Effects
{
	public partial class TestPage : ContentPage
	{
		public TestPage()
		{
			InitializeComponent();

			var presets = new List<Preset>();

			// Presets
			defaultPreset = new Preset();
			defaultPreset.Name = "(Default)";
			defaultPreset.UseGlassEffect = false;
			defaultPreset.GlassEffectType = GlassEffectType.Type1;
			defaultPreset.UsePenAsGlow = false;
			defaultPreset.GlowColor = Colors.White;
			defaultPreset.ReflectionColor = Colors.White;
			defaultPreset.UseAeroEffect = false;
			defaultPreset.Opacity = 40;
			defaultPreset.InnerOutlineColor = Colors.White;
			defaultPreset.ShadeColor = Colors.Black;
			defaultPreset.OverridesThemeSettings = true;
			defaultPreset.CalendarBackground = Colors.White;
			defaultPreset.CalendarBorder = Colors.DarkSlateGray;
			defaultPreset.ItemBorder = Colors.DarkSlateGray;
			defaultPreset.ItemBackground1 = Colors.LightSteelBlue;
			defaultPreset.UseItemBackground2 = false;
			defaultPreset.ItemBackground2 = Colors.White;
			defaultPreset.Theme = ThemeType.Standard;

			var preset = new Preset();
			preset.Name = "Glass & Aero (Standard Theme)";
			preset.UseGlassEffect = true;
			preset.GlassEffectType = GlassEffectType.Type1;
			preset.UsePenAsGlow = false;
			preset.GlowColor = Colors.White;
			preset.ReflectionColor = Colors.White;
			preset.UseAeroEffect = true;
			preset.Opacity = 40;
			preset.InnerOutlineColor = Colors.White;
			preset.ShadeColor = Colors.Black;
			preset.OverridesThemeSettings = false;
			preset.CalendarBackground = Colors.White;
			preset.CalendarBorder = Colors.DarkSlateGray;
			preset.ItemBorder = Colors.DarkSlateGray;
			preset.ItemBackground1 = Colors.LightSteelBlue;
			preset.UseItemBackground2 = false;
			preset.ItemBackground2 = Colors.White;
			preset.Theme = ThemeType.Standard;
			presets.Add(preset);

			preset = new Preset();
			preset.Name = "Glass & Aero (Light Theme)";
			preset.UseGlassEffect = true;
			preset.GlassEffectType = GlassEffectType.Type1;
			preset.UsePenAsGlow = false;
			preset.GlowColor = Colors.White;
			preset.ReflectionColor = Colors.White;
			preset.UseAeroEffect = true;
			preset.Opacity = 40;
			preset.InnerOutlineColor = Colors.White;
			preset.ShadeColor = Colors.Black;
			preset.OverridesThemeSettings = false;
			preset.CalendarBackground = Colors.White;
			preset.CalendarBorder = Colors.DarkSlateGray;
			preset.ItemBorder = Colors.DarkSlateGray;
			preset.ItemBackground1 = Colors.LightSteelBlue;
			preset.UseItemBackground2 = false;
			preset.ItemBackground2 = Colors.White;
			preset.Theme = ThemeType.Light;
			presets.Add(preset);

			preset = new Preset();
			preset.Name = "Glass & Aero (Dark Theme)";
			preset.UseGlassEffect = true;
			preset.GlassEffectType = GlassEffectType.Type1;
			preset.UsePenAsGlow = false;
			preset.GlowColor = Colors.White;
			preset.ReflectionColor = Colors.White;
			preset.UseAeroEffect = true;
			preset.Opacity = 40;
			preset.InnerOutlineColor = Colors.White;
			preset.ShadeColor = Colors.Black;
			preset.OverridesThemeSettings = false;
			preset.CalendarBackground = Colors.White;
			preset.CalendarBorder = Colors.DarkSlateGray;
			preset.ItemBorder = Colors.DarkSlateGray;
			preset.ItemBackground1 = Colors.LightSteelBlue;
			preset.UseItemBackground2 = false;
			preset.ItemBackground2 = Colors.White;
			preset.Theme = ThemeType.Dark;
			presets.Add(preset);

			preset = new Preset();
			preset.Name = "Glass (Default Colors)";
			preset.UseGlassEffect = true;
			preset.GlassEffectType = GlassEffectType.Type1;
			preset.UsePenAsGlow = false;
			preset.GlowColor = Colors.White;
			preset.ReflectionColor = Colors.White;
			preset.UseAeroEffect = false;
			preset.Opacity = 40;
			preset.InnerOutlineColor = Colors.White;
			preset.ShadeColor = Colors.Black;
			preset.OverridesThemeSettings = true;
			preset.CalendarBackground = Colors.White;
			preset.CalendarBorder = Colors.DarkSlateGray;
			preset.ItemBorder = Colors.DarkSlateGray;
			preset.ItemBackground1 = Colors.LightSteelBlue;
			preset.UseItemBackground2 = false;
			preset.ItemBackground2 = Colors.White;
			preset.Theme = ThemeType.Standard;
			presets.Add(preset);

			preset = new Preset();
			preset.Name = "Glass & Aero (Orange Background)";
			preset.UseGlassEffect = true;
			preset.GlassEffectType = GlassEffectType.Type2;
			preset.UsePenAsGlow = false;
			preset.GlowColor = Colors.White;
			preset.ReflectionColor = Colors.White;
			preset.UseAeroEffect = true;
			preset.Opacity = 20;
			preset.InnerOutlineColor = Colors.White;
			preset.ShadeColor = Colors.Black;
			preset.OverridesThemeSettings = true;
			preset.CalendarBackground = Colors.Orange;
			preset.CalendarBorder = Colors.DarkGoldenrod;
			preset.ItemBorder = Colors.DarkSlateGray;
			preset.ItemBackground1 = Colors.LightSteelBlue;
			preset.UseItemBackground2 = false;
			preset.ItemBackground2 = Colors.White;
			preset.Theme = ThemeType.Standard;
			presets.Add(preset);

			preset = new Preset();
			preset.Name = "Metalic";
			preset.UseGlassEffect = true;
			preset.GlassEffectType = GlassEffectType.Type2;
			preset.UsePenAsGlow = true;
			preset.GlowColor = Colors.White;
			preset.ReflectionColor = Colors.White;
			preset.UseAeroEffect = true;
			preset.Opacity = 50;
			preset.InnerOutlineColor = Colors.White;
			preset.ShadeColor = Colors.Black;
			preset.OverridesThemeSettings = true;
			preset.CalendarBackground = Color.FromRgb(0xC0, 0xC0, 0xC0);
			preset.CalendarBorder = Colors.DarkSlateGray;
			preset.ItemBorder = Colors.DarkSlateGray;
			preset.ItemBackground1 = Colors.LightSteelBlue;
			preset.UseItemBackground2 = false;
			preset.ItemBackground2 = Colors.White;
			preset.Theme = ThemeType.Standard;
			presets.Add(preset);

			preset = new Preset();
			preset.Name = "Neon Glow";
			preset.UseGlassEffect = true;
			preset.GlassEffectType = GlassEffectType.Type2;
			preset.UsePenAsGlow = true;
			preset.GlowColor = Colors.White;
			preset.ReflectionColor = Colors.White;
			preset.UseAeroEffect = true;
			preset.Opacity = 0;
			preset.InnerOutlineColor = Colors.Black;
			preset.ShadeColor = Color.FromRgb(0x80, 0xFF, 0xFF);
			preset.OverridesThemeSettings = true;
			preset.CalendarBackground = Colors.Black;
			preset.CalendarBorder = Color.FromRgb(0x80, 0xFF, 0xFF);
			preset.ItemBorder = Color.FromRgb(0x80, 0xFF, 0xFF);
			preset.ItemBackground1 = Colors.Black;
			preset.UseItemBackground2 = false;
			preset.ItemBackground2 = Colors.White;
			preset.Theme = ThemeType.Standard;
			presets.Add(preset);

			preset = new Preset();
			preset.Name = "Gray glass";
			preset.UseGlassEffect = true;
			preset.GlassEffectType = GlassEffectType.Type3;
			preset.UsePenAsGlow = false;
			preset.GlowColor = Colors.White;
			preset.ReflectionColor = Colors.White;
			preset.UseAeroEffect = true;
			preset.Opacity = 40;
			preset.InnerOutlineColor = Colors.White;
			preset.ShadeColor = Colors.Black;
			preset.OverridesThemeSettings = true;
			preset.CalendarBackground = Colors.Gray;
			preset.CalendarBorder = Colors.Black;
			preset.ItemBorder = Colors.Black;
			preset.ItemBackground1 = Colors.LightCyan;
			preset.UseItemBackground2 = true;
			preset.ItemBackground2 = Color.FromRgb(0xA8, 0xBF, 0xBF);
			preset.Theme = ThemeType.Standard;
			presets.Add(preset);

			preset = new Preset();
			preset.Name = "Brown";
			preset.UseGlassEffect = true;
			preset.GlassEffectType = GlassEffectType.Type1;
			preset.UsePenAsGlow = false;
			preset.GlowColor = Colors.White;
			preset.ReflectionColor = Colors.White;
			preset.UseAeroEffect = false;
			preset.Opacity = 40;
			preset.InnerOutlineColor = Colors.White;
			preset.ShadeColor = Colors.Black;
			preset.OverridesThemeSettings = true;
			preset.CalendarBackground = Colors.PaleGoldenrod;
			preset.CalendarBorder = Colors.Black;
			preset.ItemBorder = Colors.Black;
			preset.ItemBackground1 = Color.FromRgba(0x90, 0x30, 0x20, 0x80);
			preset.UseItemBackground2 = false;
			preset.ItemBackground2 = Colors.White;
			preset.Theme = ThemeType.Standard;
			presets.Add(preset);

			preset = new Preset();
			preset.Name = "Bright Red";
			preset.UseGlassEffect = true;
			preset.GlassEffectType = GlassEffectType.Type4;
			preset.UsePenAsGlow = false;
			preset.GlowColor = Colors.Black;
			preset.ReflectionColor = Colors.White;
			preset.UseAeroEffect = false;
			preset.Opacity = 40;
			preset.InnerOutlineColor = Colors.White;
			preset.ShadeColor = Colors.Black;
			preset.OverridesThemeSettings = true;
			preset.CalendarBackground = Colors.White;
			preset.CalendarBorder = Color.FromRgb(0x40, 0x0, 0x0);
			preset.ItemBorder = Color.FromRgb(0x60, 0x0, 0x0);
			preset.ItemBackground1 = Colors.Red;
			preset.UseItemBackground2 = false;
			preset.ItemBackground2 = Colors.White;
			preset.Theme = ThemeType.Standard;
			presets.Add(preset);

			effectsList.ItemsSource = presets;
			effectsList.RowHeight = 24;
			effectsList.ItemSelected += (s, e) =>
			{
				ApplyPreset((Preset)e.SelectedItem);
			};

			themeList.ItemsSource = new [] {
				ThemeType.Standard,
				ThemeType.Light,
				ThemeType.Dark
			};
			themeList.RowHeight = 24;
			themeList.ItemSelected += (s, e) =>
			{
				calendar.Theme = (ThemeType)e.SelectedItem;
			};

			glassEffect = new GlassEffect();
			aeroEffect = new AeroEffect();

			// Defaults
			calendar.BeginInit();
			calendar.CurrentView = CalendarView.Timetable;
			calendar.ItemSettings.ShadowStyle = ShadowStyle.None;
			calendar.ItemSettings.Size = 24;
			calendar.TimetableSettings.Dates.Clear();
			calendar.TimetableSettings.Dates.Add(DateTime.Today);
			calendar.TimetableSettings.ShowDayHeader = State.Enabled;
			calendar.EndInit();

			// Create some preview items
			Appointment app;

			app = new Appointment();
			app.StartTime = DateTime.Today;
			app.EndTime = DateTime.Today;
			app.AllDayEvent = true;
			app.HeaderText = "All-day appointment";
			calendar.Schedule.Items.Add(app);

			app = new Appointment();
			app.StartTime = DateTime.Today.AddHours(2);
			app.EndTime = DateTime.Today.AddHours(4);
			app.HeaderText = "Event #1";
			app.DescriptionText = "Description for Event #1.";
			calendar.Schedule.Items.Add(app);

			app = new Appointment();
			app.StartTime = DateTime.Today.AddHours(3);
			app.EndTime = DateTime.Today.AddHours(4.5);
			app.HeaderText = "Event #2";
			app.DescriptionText = "Description for Event #2.";
			calendar.Schedule.Items.Add(app);

			app = new Appointment();
			app.StartTime = DateTime.Today.AddHours(5);
			app.EndTime = DateTime.Today.AddHours(6);
			app.HeaderText = "Event #3";
			app.DescriptionText = "Description for Event #3.";
			calendar.Schedule.Items.Add(app);
		}

		void ApplyPreset(Preset preset)
		{
			if (preset.UseGlassEffect)
			{
				if (!calendar.ItemEffects.Contains(glassEffect))
					calendar.ItemEffects.Add(glassEffect);
			}
			else
			{
				if (calendar.ItemEffects.Contains(glassEffect))
					calendar.ItemEffects.Remove(glassEffect);
			}

			glassEffect.Type = preset.GlassEffectType;
			glassEffect.UsePenAsGlow = preset.UsePenAsGlow;
			glassEffect.GlowColor = preset.GlowColor;
			glassEffect.ReflectionColor = preset.ReflectionColor;

			if (preset.UseAeroEffect)
			{
				if (!calendar.ItemEffects.Contains(aeroEffect))
					calendar.ItemEffects.Add(aeroEffect);
			}
			else
			{
				if (calendar.ItemEffects.Contains(aeroEffect))
					calendar.ItemEffects.Remove(aeroEffect);
			}

			aeroEffect.Opacity = (float)preset.Opacity / 100;
			aeroEffect.ShadeColor = preset.ShadeColor;
			aeroEffect.InnerOutlineColor = preset.InnerOutlineColor;

			if (preset.OverridesThemeSettings)
			{
				SetStyleBrushes(calendar.TimetableSettings.CellStyle, new SolidBrush(preset.CalendarBackground));
				SetStyleBrushes(calendar.TimetableSettings.WorkTimeCellStyle, new SolidBrush(preset.CalendarBackground));
				calendar.TimetableSettings.DayHeaderBrush = new SolidBrush(preset.CalendarBackground);
				SetStyleBorders(calendar.TimetableSettings.CellStyle, preset.CalendarBorder);
				SetStyleBorders(calendar.TimetableSettings.WorkTimeCellStyle, preset.CalendarBorder);
				SetStyleBorders(calendar.ItemSettings.Style, preset.ItemBorder);
				SetStyleBorders(calendar.ItemSettings.SelectedItemStyle, preset.ItemBorder);
				SetStyleBorders(calendar.ItemSettings.PointedItemStyle, preset.ItemBorder);
				SetStyleBorders(calendar.ItemSettings.PointedSelectedItemStyle, preset.ItemBorder);
				calendar.ItemSettings.Style.LineColor = preset.ItemBorder;
				calendar.ItemSettings.SelectedItemStyle.LineColor = preset.ItemBorder;
				calendar.ItemSettings.PointedItemStyle.LineColor = preset.ItemBorder;
				calendar.ItemSettings.PointedSelectedItemStyle.LineColor = preset.ItemBorder;
				calendar.ItemSettings.Style.TextColor = preset.ItemBorder;
				calendar.ItemSettings.SelectedItemStyle.TextColor = preset.ItemBorder;
				calendar.ItemSettings.PointedItemStyle.TextColor = preset.ItemBorder;
				calendar.ItemSettings.PointedSelectedItemStyle.TextColor = preset.ItemBorder;
				calendar.ItemSettings.Style.HeaderTextColor = preset.ItemBorder;
				calendar.ItemSettings.SelectedItemStyle.HeaderTextColor = preset.ItemBorder;
				calendar.ItemSettings.PointedItemStyle.HeaderTextColor = preset.ItemBorder;
				calendar.ItemSettings.PointedSelectedItemStyle.HeaderTextColor = preset.ItemBorder;
				UpdateItemBrush(preset);
			}
			else
			{
				ResetStyleBrushes(calendar.TimetableSettings.CellStyle);
				ResetStyleBrushes(calendar.TimetableSettings.WorkTimeCellStyle);
				calendar.TimetableSettings.DayHeaderBrush = null;
				ResetStyleBorders(calendar.TimetableSettings.CellStyle);
				ResetStyleBorders(calendar.TimetableSettings.WorkTimeCellStyle);

				ResetStyleBorders(calendar.ItemSettings.Style);
				ResetStyleBorders(calendar.ItemSettings.SelectedItemStyle);
				ResetStyleBorders(calendar.ItemSettings.PointedItemStyle);
				ResetStyleBorders(calendar.ItemSettings.PointedSelectedItemStyle);
				calendar.ItemSettings.Style.LineColor = Colors.Empty;
				calendar.ItemSettings.SelectedItemStyle.LineColor = Colors.Empty;
				calendar.ItemSettings.PointedItemStyle.LineColor = Colors.Empty;
				calendar.ItemSettings.PointedSelectedItemStyle.LineColor = Colors.Empty;
				calendar.ItemSettings.Style.TextColor = Colors.Empty;
				calendar.ItemSettings.SelectedItemStyle.TextColor = Colors.Empty;
				calendar.ItemSettings.PointedItemStyle.TextColor = Colors.Empty;
				calendar.ItemSettings.PointedSelectedItemStyle.TextColor = Colors.Empty;
				calendar.ItemSettings.Style.HeaderTextColor = Colors.Empty;
				calendar.ItemSettings.SelectedItemStyle.HeaderTextColor = Colors.Empty;
				calendar.ItemSettings.PointedItemStyle.HeaderTextColor = Colors.Empty;
				calendar.ItemSettings.PointedSelectedItemStyle.HeaderTextColor = Colors.Empty;

				ResetStyleBrushes(calendar.ItemSettings.Style);
				ResetStyleBrushes(calendar.ItemSettings.SelectedItemStyle);
				ResetStyleBrushes(calendar.ItemSettings.PointedItemStyle);
				ResetStyleBrushes(calendar.ItemSettings.PointedSelectedItemStyle);
				calendar.ItemSettings.Style.FillColor = Colors.Empty;
				calendar.ItemSettings.SelectedItemStyle.FillColor = Colors.Empty;
				calendar.ItemSettings.PointedItemStyle.FillColor = Colors.Empty;
				calendar.ItemSettings.PointedSelectedItemStyle.FillColor = Colors.Empty;
			}

			if (preset.Theme.HasValue)
				themeList.SelectedItem = preset.Theme.Value;
		}

		void UpdateItemBrush(Preset preset)
		{
			Brush brush;
			if (!preset.UseItemBackground2)
				brush = new SolidBrush(preset.ItemBackground1);
			else
				brush = new LinearGradientBrush(preset.ItemBackground1, preset.ItemBackground2, 90);

			SetStyleBrushes(calendar.ItemSettings.Style, brush);
			SetStyleBrushes(calendar.ItemSettings.SelectedItemStyle, brush);
			SetStyleBrushes(calendar.ItemSettings.PointedItemStyle, brush);
			SetStyleBrushes(calendar.ItemSettings.PointedSelectedItemStyle, brush);

			calendar.ItemSettings.Style.FillColor = preset.ItemBackground1;
			calendar.ItemSettings.SelectedItemStyle.FillColor = preset.ItemBackground1;
			calendar.ItemSettings.PointedItemStyle.FillColor = preset.ItemBackground1;
			calendar.ItemSettings.PointedSelectedItemStyle.FillColor = preset.ItemBackground1;
		}

		static void SetStyleBorders(MindFusion.Scheduling.Style style, Color color)
		{
			style.BorderLeftColor = color;
			style.BorderTopColor = color;
			style.BorderRightColor = color;
			style.BorderBottomColor = color;
			style.HeaderBorderLeftColor = color;
			style.HeaderBorderTopColor = color;
			style.HeaderBorderRightColor = color;
			style.HeaderBorderBottomColor = color;
		}

		static void SetStyleBrushes(MindFusion.Scheduling.Style style, Brush brush)
		{
			style.Brush = brush;
			style.HeaderBrush = brush;
		}

		static void ResetStyleBorders(MindFusion.Scheduling.Style style)
		{
			style.BorderLeftColor = Colors.Empty;
			style.BorderTopColor = Colors.Empty;
			style.BorderRightColor = Colors.Empty;
			style.BorderBottomColor = Colors.Empty;
			style.HeaderBorderLeftColor = Colors.Empty;
			style.HeaderBorderTopColor = Colors.Empty;
			style.HeaderBorderRightColor = Colors.Empty;
			style.HeaderBorderBottomColor = Colors.Empty;
		}

		static void ResetStyleBrushes(MindFusion.Scheduling.Style style)
		{
			style.Brush = null;
			style.HeaderBrush = null;
		}


		GlassEffect glassEffect;
		AeroEffect aeroEffect;
		Preset defaultPreset;

		class Preset
		{
			public override string ToString()
			{
				return Name;
			}

			public string Name;

			// Glass
			public bool UseGlassEffect;
			public GlassEffectType GlassEffectType;
			public bool UsePenAsGlow;
			public Color GlowColor;
			public Color ReflectionColor;

			// Aero
			public bool UseAeroEffect;
			public int Opacity;
			public Color InnerOutlineColor;
			public Color ShadeColor;

			// Misc
			public bool OverridesThemeSettings;
			public Color CalendarBackground;
			public Color CalendarBorder;
			public Color ItemBorder;
			public Color ItemBackground1;
			public bool UseItemBackground2;
			public Color ItemBackground2;

			public ThemeType? Theme;
		}
	}
}