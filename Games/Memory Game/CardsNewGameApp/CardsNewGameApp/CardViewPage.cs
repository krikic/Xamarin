using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;
using Xamarin.Forms;

namespace CardsNewGameApp
{
    public class CardViewPage : ContentPage
    {
        private List<Card> data = Game.GetCardsData();
        private static string[] ImagesPath={"C0.png", "C1.png", "C2.png", "C3.png", "C4.png",
            "C5.png", "C6.png", "C7.png", "C8.png", "C9.png", "C10.png"};

       
       
        //private static bool isEnable1 =true, isEnable2 = true;
        //private static bool isSelected1 = true, isSelected2 = true;
        private static Image Removeimg;
        private int rowCard ,colCard;
        private Label AttemptsScore, ErrorsScore, RightsScore;
        private Grid grid;
        private Image image, DefualtImage;
        private StackLayout ScoreLayout;
         private int CardValue1=0, CardValue2=0,incrmt=0;
        private Card card;


        public CardViewPage()
        {
            
            Title = "Game page";
            Padding = new Thickness(5, Device.OnPlatform(20, 5, 0),5.0,5.0);
    
            Label attemptslbl = new Label
            {
                Text="Attempts:",
                HorizontalOptions =LayoutOptions.Start
            };
            Label errorslbl = new Label
            {
                Text = "Errors:",
                HorizontalOptions = LayoutOptions.Start
            };
            Label Rightslbl = new Label
            {
                Text = "Rights:",
                HorizontalOptions = LayoutOptions.Start
            };
            AttemptsScore = new Label
            {
                Text = "0",
                 HorizontalOptions = LayoutOptions.Start
                ,
                  BackgroundColor=Color.Red
                , TextColor=Color.White
            };
            ErrorsScore = new Label
            {
                Text = "0",
                HorizontalOptions = LayoutOptions.Start
               ,
                BackgroundColor = Color.Red
               ,
                TextColor = Color.White
            };
            RightsScore = new Label
            {
                Text = "0",
                HorizontalOptions = LayoutOptions.Start
               ,
                BackgroundColor = Color.Red
               ,
                TextColor = Color.White
            };
            ScoreLayout = new StackLayout
            {
                Orientation= StackOrientation.Horizontal,
                Children = {attemptslbl, AttemptsScore,errorslbl,ErrorsScore,Rightslbl,RightsScore },
                Padding=new Thickness(2,2,0,5)
                ,Spacing=15
            };
           //backButton = new Button
           // {
           //     Text = "Back to the main page",              
           //     HorizontalOptions = LayoutOptions.Center,
           //     VerticalOptions = LayoutOptions.Center,
           //     BackgroundColor=Color.Red
           // };
            //go back to the main page
            //backButton.Clicked += async (s, a) =>
            //{
            //    await Navigation.PopAsync();

            //};

            incrmt = 0;
            SaveAndGetImagesToList.init();
          
            DrawImages();//initial image

                  
            Content = new StackLayout
            {
                VerticalOptions=LayoutOptions.CenterAndExpand,
                HorizontalOptions=LayoutOptions.CenterAndExpand,

                Children = {
                  ScoreLayout, grid
                }
            };
           
        }

        private async void tapImage_Tapped(object sender, EventArgs e)
        {

            var row = (int)((BindableObject)sender).GetValue(Grid.RowProperty);
            var col = (int)((BindableObject)sender).GetValue(Grid.ColumnProperty);

            var CardValue = ((BindableObject)sender).GetValue(BindingContextProperty);//Get value of card
            var IsEnable = (bool)((BindableObject)sender).GetValue(IsEnabledProperty);//Get enable value
           
         
          try
             {
                  if (IsEnable)
                  {
                    incrmt++;
                    var bb = ImagesPath[int.Parse(CardValue.ToString())];
                    ((BindableObject)sender).SetValue(Image.SourceProperty, bb);//set the new source for card

                    ((BindableObject)sender).SetValue(Image.IsEnabledProperty, false);//set the new  enable property for  card
                    DependencyService.Get<ICardsSound>().Vibration("cardPlace2.wav");
                   

                    if (incrmt >= 3) incrmt = 0;

                    if (incrmt == 1)
                    {
                        CardValue1 = int.Parse(CardValue.ToString());
                        //first image which it will be removing from grid propobly
                        Removeimg = SaveAndGetImagesToList.GetImageFromList(row, col);//get image  from list
                        var id = SaveAndGetImagesToList.GetIDFromList(row, col);
                        var TapImge = SaveAndGetImagesToList.GetTapGestureRecognizerFromList(row, col);

                        DefualtImage= SaveAndGetImagesToList.GetImageFromList(row, col);
                        card= data.Where(a => a.CardId ==id ).FirstOrDefault();
                        rowCard = row; colCard = col;
 
                    }

                    if (incrmt == 2) { CardValue2 = int.Parse(CardValue.ToString()); incrmt = 0; }

                    if (CardValue1 > 0 && CardValue2 > 0)
                    {
                        if (CardValue1.Equals(CardValue2))
                        {
                           var right = RightsScore.GetValue(Label.TextProperty);// Get the value from label text   
                            DependencyService.Get<ICardsSound>().ShowToast("Excellent!!..you have succeeded in choosing the card.!");

                            await Task.Delay(600);

                            RightsScore.SetValue(Label.TextProperty, int.Parse(right.ToString()) + 1);// increase the result that is in label and show it in label                            
                            ((BindableObject)sender).SetValue(Image.IsVisibleProperty, false);//set the new  enable property for  card
                            grid.Children.Remove(Removeimg);
                        }
                        else
                        {
                            var error = ErrorsScore.GetValue(Label.TextProperty);
                            ErrorsScore.SetValue(Label.TextProperty, int.Parse(error.ToString()) + 1);

                            await Task.Delay(600);
                            // property for the second image
                            ((BindableObject)sender).SetValue(Image.SourceProperty, ImagesPath[0]);

                            ((BindableObject)sender).SetValue(Image.IsEnabledProperty, true);

                            //remove first image and set defualt on the som place
                            grid.Children.Remove(Removeimg);

                            //Create new image as defualt with the old data
                            DefualtImage.Source = ImagesPath[0];
                            DefualtImage.IsEnabled = true;
                            DefualtImage.IsVisible = card.isVisibleImage;
                            DefualtImage.ClassId = card.CardId.ToString();
                            DefualtImage.BindingContext = card.CardValue;
                            grid.Children.Add(DefualtImage,colCard,rowCard);
                         
                            DependencyService.Get<ICardsSound>().ShowToast("We wish you good luck another time.. try again.!");
                        }

                       var  Result = AttemptsScore.GetValue(Label.TextProperty);
                        AttemptsScore.SetValue(Label.TextProperty, int.Parse(Result.ToString()) + 1);
                        CardValue1 = 0; CardValue2 = 0;


                        incrmt = 0;

                    }
                }

            }catch (Exception ex) { Debug.WriteLine(ex.Message); }
        }

        private  void DrawImages(int CardNum = 0)
        {
            var i = 0;//row
            var j = 0;//column
            //grid.Children.Clear();
            grid = new Grid();
            foreach (var z in data)
            {
              
                //Create a new card
                image = new Image
                {
                    Source = ImagesPath[CardNum],
                    VerticalOptions = LayoutOptions.CenterAndExpand,
                    HorizontalOptions = LayoutOptions.CenterAndExpand,
                    IsEnabled = true,
                    IsVisible = true,
                    ClassId = z.CardId.ToString(),
                    BindingContext = z.CardValue
                };

                //Creating TapGestureRecognizers  
               var tapImage = new TapGestureRecognizer();

                //Binding events  
                tapImage.Tapped += tapImage_Tapped;

                //Associating tap events to the image buttons  
                image.GestureRecognizers.Add(tapImage);

                if (j <= 3)
                {
                    grid.Children.Add(image, j, i);
                    SaveAndGetImagesToList.AddNewIems(i, j, z.CardId, image,tapImage);//add new image with NEW coordinate

                    j++;
                }
                else
                {
                    i = i + 1;

                    j = 0;
                    SaveAndGetImagesToList.AddNewIems(i, j, z.CardId, image,tapImage);//add new image 
                    grid.Children.Add(image, j, i);
                    j = 1;
                }
                //DependencyService.Get<ICardsSound>().Vibration("chipsCollide.wav");
                //await Task.Delay(30);
            }


        }
    }
}
