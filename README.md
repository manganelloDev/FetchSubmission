## FetchSubmission
Hello, my name is Ryan Manganello. I have over 2 years of Android development experience from my time at General Motors. During my time at GM, my scrum and I developed various android applications that displayed on the radio screens of Chevy, Buick, GMC, and Cadillac vehicles. The apps I developed covered a wide variety of usecases. Some involved extensive usages of external APIs and SDKs, while others required significant interfacing with GM internal software that was ran on various modules throughout the vehicles.
## Setup
- Built using <code>Android Studio Koala Feature Drop | 2024.1.2</code>
- Tested on <code>Pixel 7 API 34</code> emulator
## Assumptions
- No unit testing expected
- No espresso testing expected
- Remote data set is constant and it's format will not change
## Design
- I designed my app to include a spinner which allows the user to display all data or portions of the data
- Due to the large size of the remote data, I opted to display the data with a recycler view in order to maintain system performance
- I included a stationary heading to clearly show what each column of data represents
- I included distinct color coding and group headers for users to easily identify the beginning and end of each section
- To ensure separation of concerns, I opted to pull, sort, and filter the remote data in a viewModel and post to a LiveData variable observed in the fragment
## Notes
Per your request I can continue to build out this project and display my abilities
  - Error handling - Build UI to indicate to the user when the app is having issues pulling the remote data
  - UI Testing - Develop Espresso Tests to ensure proper UI functionality following future updates
  - Unit Testing - Develop Unit Tests for non-UI logic to ensure proper functionality following future updates
  - Other - Anything you may want to see my abilities in, please ask!