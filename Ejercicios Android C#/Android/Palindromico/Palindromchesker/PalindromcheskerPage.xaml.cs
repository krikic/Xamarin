using System;
using Xamarin.Forms;

namespace Palindromchesker
{
	public partial class PalindromcheskerPage : ContentPage
	{
		//inicalizuje xaml aplikace
		public PalindromcheskerPage()
		{
			InitializeComponent();

		}
		//funkce se zeptá zda je řetezec pal......
		public void odeslat(object sender, EventArgs e)
		{
			if (jePalindrom(text.Text) == true)
			{
				vys.Text = "je";
			}
			else
			{
				vys.Text = "není";
			}
		}
		//vlastní kontrola
		public static bool jePalindrom(string retezec)
		{
			//vstupní řetezec je převeden na malá pismena a nasledně jsou nahrazeny české znaky a specialní znaky
			retezec = retezec.ToLower()
							 .Replace(" ", "")
							 .Replace(".", "")
							 .Replace("!", "")
							 .Replace("?", "")
							 .Replace("ě", "e")
							 .Replace("š", "s")
							 .Replace("č", "c")
							 .Replace("ř", "r")
							 .Replace("ž", "z")
							 .Replace("ý", "y")
							 .Replace("á", "a")
							 .Replace("í", "i")
							 .Replace("é", "e");
			//řetezec je rozdělen do pole
			char[] array = retezec.ToCharArray();
			//pole je otočeno
			Array.Reverse(array);
			//z obraceného pole zpět na řetězec
			string backwards = new string(array);
			//porovnání a nasledé rozhodnutí je/není
			if (retezec == backwards)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}
}
