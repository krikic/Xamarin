using System;
using System.Collections.Generic;
using System.Linq;
using Xamarin.Forms;

namespace CardsNewGameApp
{
    public static class Game
    {
        private const int MATROWS = 16;
        private static List<Card> Cards;

        // start new game
        private static int[] mat = new int[MATROWS] { 2, 1, 6, 8, 4, 9, 2, 9, 1, 6, 4, 8,3,3,5,5};

        public static List<Player> GetPlayerData()
        {
            return new List<Player>{
                new Player()
               {
                  PlayerId = 1,
                  Name = "Issa"
                }
            };
        }


        //method below to shuffle the array content after you initialize the array
        public static void ShuffleArr<T>(T[] arr)
        {
            Random rng = new Random();
            int n = arr.Length;
            while (n > 1)
            {
                n--;
                int k = rng.Next(n + 1);
                T value = arr[k];
                arr[k] = arr[n];
                arr[n] = value;
            }
        }
        public static List<Card> GetCardsData()
        {

            ShuffleArr(mat);

            //return new List<Card>(){

            //new Card {
            //        CardId=1, isSelectedCard=true, CardValue=mat[0], isVisibleImage=true
            //    },
            //    new Card()
            //    {
            //         CardId=2, isSelectedCard=true, CardValue=mat[1], isVisibleImage=true
            //    },
            //     new Card()
            //    {
            //         CardId=3, isSelectedCard=true, CardValue=mat[2], isVisibleImage=true
            //    },
            //    new Card()
            //    {
            //         CardId=4, isSelectedCard=true, CardValue=mat[3], isVisibleImage=true
            //    },
            //     new Card()
            //    {
            //         CardId=5, isSelectedCard=true, CardValue=mat[4], isVisibleImage=true
            //    },
            //    new Card()
            //    {
            //         CardId=6, isSelectedCard=true, CardValue=mat[5], isVisibleImage=true
            //    },
            //     new Card()
            //    {
            //         CardId=7, isSelectedCard=true, CardValue=mat[6], isVisibleImage=true
            //    },
            //    new Card()
            //    {
            //         CardId=8, isSelectedCard=true, CardValue=mat[7], isVisibleImage=true
            //    },
            //     new Card()
            //    {
            //         CardId=9, isSelectedCard=true, CardValue=mat[8], isVisibleImage=true
            //    },
            //    new Card()
            //    {
            //         CardId=10, isSelectedCard=true, CardValue=mat[9], isVisibleImage=true
            //    }, new Card()
            //    {
            //         CardId=11, isSelectedCard=true, CardValue=mat[10], isVisibleImage=true
            //    },
            //    new Card()
            //    {
            //         CardId=12, isSelectedCard=true, CardValue=mat[11], isVisibleImage=true
            //    }
            //};
           // Make new cards
            Cards = new List<Card>();
            for (int i = 1; i <= MATROWS; i++)
                Cards.Add(new Card()
                {
                    CardId = i,
                    isSelectedCard = true,
                    CardValue = mat[i - 1],
                    isVisibleImage = true

                });

            return Cards;

        }


    }

}
