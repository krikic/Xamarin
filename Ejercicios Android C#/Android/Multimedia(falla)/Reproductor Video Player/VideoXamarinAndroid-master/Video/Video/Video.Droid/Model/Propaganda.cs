using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;

namespace Video.Droid.Model
{
    public class Propaganda
    {
        public int Id { get; set; }
        public string Description { get; set; }
        public DateTime DatePublichers { get; set; }
        public int Clicks { get; set; }
        public string Url { get; set; }
        public List<Driver> Driver { get; set; }
    }
}