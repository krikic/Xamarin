using System.Collections.ObjectModel;
using System.Linq;
using Xamarin.Forms;

namespace CardsNewGameApp
{
    public static class SaveAndGetImagesToList
    {
        // Images collection
        public static ObservableCollection<ImagesToList> imagetolist; 

        public static void init()
        {
            imagetolist = new ObservableCollection<ImagesToList>();
        }

        public static void AddNewIems(int row,int col,int id,Image image, TapGestureRecognizer tapImage)
        {
            imagetolist.Add(new ImagesToList() {  row=row,col=col,  id=id, image=image, tapImage=tapImage });

        }

        public static Image GetImageFromList(int row, int col)
        {
           var img= imagetolist.Where(a => a.col == col && a.row == row).Select(s => s.image).FirstOrDefault();
            return img;
        }

        public static TapGestureRecognizer GetTapGestureRecognizerFromList(int row, int col)
        {
            var tapimg = imagetolist.Where(a => a.col == col && a.row == row).Select(s => s.tapImage).FirstOrDefault();
            return tapimg;
        }

        public static int GetIDFromList(int row, int col)
        {
            var id = imagetolist.Where(a => a.col == col && a.row == row).Select(s => s.id).FirstOrDefault();
            return id;
        }
    }
}
