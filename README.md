#MoviesApp

Movies application is developed in order to implement MVVM architecture of Android app development platform. Alongside the MVVM
application contains some of the Android MVVM components (JetPack) like RxAndroid, Room, Dagger 2 (dependecy injectiom).

The main purpose of development of this project is to demonstrate the knowledge of Android MVVM, Dagger 2, Retrofit, RxAndroid
Room, Observers,Mockito, Unit testing and etc. The app does not contain very complex implementation and functionality. 

The application is developed in order to show very basic implementation of MVVM with dependecy injection (Dagger 2), Retrofit 
and all about technologies that is mentioned below. 

Application consumes data from Newyork Times Movie Reviews Api. Depending on user's searched title, list of movies
will be returned by service and user will be able to read the titles and short summary of the movies.

The application makes call from the Newyork times api which is as follows:
https://api.nytimes.com/svc/movies/v2/reviews/search.json?query=godfather&api-key=yourkey

The main functionality of the application:

    1) Application launches at first time there is no local data
    2) User enters text to the search field, list of movies with their short summary is shown at recyclerview
    3) Stores the last searched details and in case of there is not internet connection latest parsed search result is 
    shown at the recycler.
    
    
The technology that has been used in order to develop this sample project:     

Android, Kotlin, MVVM, Dagger 2, Retrofit, Room, RxAndroid,Mockito, Unit testing
