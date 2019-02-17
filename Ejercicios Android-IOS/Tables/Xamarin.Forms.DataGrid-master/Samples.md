
#**Xamarin.Forms.DataGrid**
    
> **Samples**
  >- Custom Sorting Image
  >- Usage With Resource dictionary



---

### Custom Sorting Image ###
> Note : Default sorting icon size is 9x5(w*h). If you need to use bigger icon you need to override `AscendingIconStyle` and `DescendingIconStyle` like below.

```xaml
    xmlns:dg="clr-namespace:Xamarin.Forms.DataGrid;assembly=Xamarin.Forms.DataGrid"
    xmlns:conv="clr-namespace:DataGridSample.Views.Converters;assembly=DataGridSample"

<dg:DataGrid ItemsSource="{Binding Teams}" RowHeight="70" HeaderHeight="50"
             BorderColor="#CCCCCC" HeaderBackground="#E0E6F8"
             AscendingIconStyle="{StaticResource AscendingIconStyle}"
             DescendingIconStyle="{StaticResource DescendingIconStyle}">
            <dg:DataGrid.HeaderFontSize>
                <OnIdiom  x:TypeArguments="x:Double">
                    <OnIdiom.Tablet>15</OnIdiom.Tablet>
                    <OnIdiom.Phone>12</OnIdiom.Phone>
                </OnIdiom>
            </dg:DataGrid.HeaderFontSize>
            <dg:DataGrid.Columns>
                <dg:DataGridColumn Title="Logo" PropertyName="Logo" Width="100" SortingEnabled="False">
                    <dg:DataGridColumn.CellTemplate>
                        <DataTemplate>
                            <Image Source="{Binding Logo}" HorizontalOptions="Center" VerticalOptions="Center" Aspect="AspectFit" HeightRequest="60" />
                        </DataTemplate>
                    </dg:DataGridColumn.CellTemplate>
                </dg:DataGridColumn>
                <dg:DataGridColumn Title="Team" PropertyName="Name" Width="2*"/>
                <dg:DataGridColumn Title="Win" PropertyName="Win" Width="0.95*"/>
                <dg:DataGridColumn Title="Loose" PropertyName="Loose"  Width="1*"/>
                <dg:DataGridColumn Title="Home" PropertyName="Home"/>
                <dg:DataGridColumn Title="Percentage" PropertyName="Percentage" StringFormat="{}{0:0.00}" />
                <dg:DataGridColumn Title="Streak" PropertyName="Streak" Width="0.7*">
                    <dg:DataGridColumn.CellTemplate>
                        <DataTemplate>
                            <ContentView HorizontalOptions="Fill" VerticalOptions="Fill"
                           BackgroundColor="{Binding Streak,Converter={StaticResource StreakToColorConverter}}">
                                <Label Text="{Binding Streak}" HorizontalOptions="Center" VerticalOptions="Center" TextColor="Black"/>
                            </ContentView>
                        </DataTemplate>
                    </dg:DataGridColumn.CellTemplate>
                </dg:DataGridColumn>
            </dg:DataGrid.Columns>
            <dg:DataGrid.RowsBackgroundColorPalette>
                <dg:PaletteCollection>
                    <Color>#F2F2F2</Color>
                    <Color>#FFFFFF</Color>
                </dg:PaletteCollection>
            </dg:DataGrid.RowsBackgroundColorPalette>
            <dg:DataGrid.Resources>
                <ResourceDictionary>
                    <conv:StreakToColorConverter x:Key="StreakToColorConverter"/>
                    <Style x:Key="SortingImageStyleBase" TargetType="Image">
                        <Setter Property="Aspect" Value="AspectFit"/>
                        <Setter Property="VerticalOptions" Value="Center"/>
                        <Setter Property="HorizontalOptions" Value="Center"/>
                        <Setter Property="HeightRequest" Value="15"/>
                        <Setter Property="WidthRequest" Value="15"/>
                        <Setter Property="Margin" Value="0,0,4,0"/>
                    </Style>
                    <Style x:Key="AscendingIconStyle" TargetType="Image" BasedOn="{StaticResource SortingImageStyleBase}">
                        <Setter Property="Source" Value="arrow_down.png"/>
                    </Style>
                    <Style x:Key="DescendingIconStyle" TargetType="Image" BasedOn="{StaticResource SortingImageStyleBase}">
                        <Setter Property="Source" Value="arrow_up.png"/>
                    </Style>
                </ResourceDictionary>
            </dg:DataGrid.Resources>
        </dg:DataGrid>
```
#### Screenshot ####

![Sorted By Wins](https://raw.githubusercontent.com/akgulebubekir/Xamarin.Forms.DataGrid/master/Screenshots/UWP_CustomSortingIconSample.png)



### Usage With `Resource Dictionary` ##

> You can locate your resources into App.Xaml.

```xaml
<ContentPage xmlns="http://xamarin.com/schemas/2014/forms"
             xmlns:x="http://schemas.microsoft.com/winfx/2009/xaml"
             x:Class="DataGridSample.MainPage"
             xmlns:dg="clr-namespace:Xamarin.Forms.DataGrid;assembly=Xamarin.Forms.DataGrid"
             xmlns:conv="clr-namespace:DataGridSample.Views.Converters;assembly=DataGridSample"
             >
	<ContentPage.Content>
		<dg:DataGrid ItemsSource="{Binding Teams}" Style="{StaticResource dgStyle}"/>
	</ContentPage.Content>
	<ContentPage.Resources>
		<ResourceDictionary>
			<!--region Colors-->
			<Color x:Key="BorderColor">#CCCCCC</Color>
			<Color x:Key="HeaderBackgroundColor">#E0F6F8</Color>
			<!--endregion-->

			<!--region sizes-->
			<OnIdiom x:Key="HeaderFontSize" x:TypeArguments="x:Double"
							 Tablet="15" Phone="12"/>
			<!--endregion-->

			<!--resources-->
			<dg:ColumnCollection x:Key="Columns">
				<dg:DataGridColumn Title="Logo" PropertyName="Logo" Width="100" SortingEnabled="False">
					<dg:DataGridColumn.CellTemplate>
						<DataTemplate>
							<Image Source="{Binding Logo}" HorizontalOptions="Center" VerticalOptions="Center" Aspect="AspectFit" HeightRequest="60" />
						</DataTemplate>
					</dg:DataGridColumn.CellTemplate>
				</dg:DataGridColumn>
				<dg:DataGridColumn Title="Team" PropertyName="Name" Width="2*"/>
				<dg:DataGridColumn Title="Win" PropertyName="Win" Width="0.95*"/>
				<dg:DataGridColumn Title="Loose" PropertyName="Loose"  Width="1*"/>
				<dg:DataGridColumn Title="Home" PropertyName="Home"/>
				<dg:DataGridColumn Title="Percentage" PropertyName="Percentage" StringFormat="{}{0:0.00}" />
				<dg:DataGridColumn Title="Streak" PropertyName="Streak" Width="0.7*">
					<dg:DataGridColumn.CellTemplate>
						<DataTemplate>
							<ContentView HorizontalOptions="Fill" VerticalOptions="Fill"
													 BackgroundColor="{Binding Streak,Converter={StaticResource StreakToColorConverter}}">
								<Label Text="{Binding Streak}" HorizontalOptions="Center" VerticalOptions="Center" TextColor="Black"/>
							</ContentView>
						</DataTemplate>
					</dg:DataGridColumn.CellTemplate>
				</dg:DataGridColumn>
			</dg:ColumnCollection>
			<dg:PaletteCollection x:Key="BgPalette">
				<Color>#F2F2F2</Color>
				<Color>#FFFFFF</Color>
			</dg:PaletteCollection>
			<conv:StreakToColorConverter x:Key="StreakToColorConverter"/>
			<!--endregion-->

			<Style x:Key="dgStyle" TargetType="dg:DataGrid">
				<Setter Property="Columns" Value="{StaticResource Columns}"/>
				<Setter Property="RowHeight" Value="70"/>
				<Setter Property="HeaderHeight" Value="50"/>
				<Setter Property="BorderColor" Value="{StaticResource BorderColor}"/>
				<Setter Property="HeaderBackground" Value="{StaticResource HeaderBackgroundColor}"/>
				<Setter Property="HeaderFontSize" Value="{StaticResource HeaderFontSize}"/>
				<Setter Property="RowsBackgroundColorPalette" Value="{StaticResource BgPalette}"/>
			</Style>
		</ResourceDictionary>

	</ContentPage.Resources>
</ContentPage>
```
