# Score-track
## Introducing Score-track: Your Ultimate Free Sports Stats-Tracker!
### Tech stack:
- Java 17
- Spring Boot v.3.1.2
- Hibernate v.6.2.6
- PostgreSQL
- MongoDB
- Redis
- Spring Security v.3.1.2
- Thymeleaf v.3.1.2
- React
- Google cloud storage
- Docker

Additional libraries used in project can be found at ```pom.xml```.

## Set up

- Docker: unsupported.

All the necessary configuration can be found in ```application.properties.example```.
To set up and run the application, you will need to create a copy of this file without the ```.example```.

- SQL configuration

   - Set up ```datasource url```, ```username``` and ```password``` if not default, ```ddl-auto``` option.

- Private keys

   - Application makes use of JWTs, requiring an asymmetric encryption. Set up public and private ```.pem``` keys and put them under the `resources/certs`. You can use [OpenSSL](https://github.com/openssl/openssl) to generate them.
   - Set up a symmetric key for remember-me (used in authentication flow) key.

- Recaptcha

   - Application makes use of Google's [reCAPTCHA](https://www.google.com/recaptcha/about/) service. You should provide a private and public keys after you have configured your reCAPTCHA account.

- Google OAuth2

  - You will have to configure Google OAuth2. Once done, paste your ```CLIENT_ID``` and ```CLIENT_SECRET``` into the ```.env``` file under the ```${GOOGLE_OAUTH2_CLIENT_ID}``` and ```${GOOGLE_OAUTH2_CLIENT_SECRET}``` variables respectively.

- Google Cloud Storage

  - Application uses GCP service which is [Google Cloud Storage](https://cloud.google.com/storage/) used to upload, store and download media and files. You will have to configure it. Download the file with the ```key``` in its name in the JSON format, name it ```gcs_key.json``` and put under the ```certs``` folder. You will also have to provide the ```project-id``` and ```bucket``` name.

- Redis

  - Application uses [Redis](https://github.com/redis/redis) to cache and store data. The default configuration is already provided. You will have to run Redis DB on port ```6379``` (default).

- GeoLite

  - Application uses [GeoLite](https://dev.maxmind.com/geoip/) to identify IP addresses. Not required. If you want to use it, set the ```geo.enabled``` property to ```true```, download the GeoIP database and place it under `resources/geo` folder. DB's format is ```mmdb```.

- ClamAV

  - Application uses [ClamAV](https://github.com/Cisco-Talos/clamav) to scan uploaded files and detect malware. Not required. If you want to use it, run ClamAV service on port  ```3310``` (default).

- React dev scripts

  - If you want to run a specific script that has not been minimized yet, you can do that by providing a link to your webpack (or other) dev server that runs it, on the desired html page (that is served by the main application). To avoid cross-origin problem, set the ```react.enable.dev``` property to true, which will mark ```localhost:3000``` as "trusted". This property must be disabled in production.

- Emails

  - Set up ```host```, ```port```, ```username```, ```password``` and ```protocol``` to allow the application to send emails. You can use any kind of email testing tool or configure an actual SMTP server. I recommend [Mailtrap](https://mailtrap.io/) or using Google's SMTP server.

- NBA Api MongoDB Database

  - Application stores business related data (sports data) in document-oriented database [MongoDB](https://www.mongodb.com/). Currently Score Track is supporting only one sport's API service of the [API-SPORTS](https://api-sports.io/) platform which is NBA Api. For each of the API, application uses different MongoDB databases. To set up the NBA Api MongoDB database, provide ```host```, ```authenticationDatabase```, ```database name```, ```username```, ```password``` and ```port```.

- Batch updates scheduling

  - Application uses scheduled tasks to update and provide latest sports data. By default, NBA Api ```teams``` data is updated daily at 12:00 local time and ```players``` at 13:00 local time, which you can change by altering cron expressions at ```nbaapi.update.team.cron``` and ```nbaapi.update.player.cron``` properties respectively.
  - You also need to set up properties for Api itself. It includes ```nbaapi.realhost```, ```nbaapi.key.name```, ```nbaapi.key.value```, ```nbaapi.host.name```, ```nbaapi.host.value```, ```nbaapi.connection.timeout```, ```nbaapi.read.timeout```.

- ```.env``` variables

  - You may have noticed that there are references to ```.env``` variables. You need to create an ```.env``` file at the root of the project. Set up ```WEB_DEFAULT_READ_TIMEOUT```, ```WEB_DEFAULT_CONNECT_TIMEOUT```, ```RM_KEY```, ```RECAPTCHA_PR_KEY```, ```RECAPTCHA_PB_KEY```, ```GOOGLE_OAUTH2_CLIENT_ID```, ```GOOGLE_OAUTH2_CLIENT_SECRET``` variables there.

- i18n

  - Score Track supports internationalization and provides localized versions depending on the location. Currently, it supports English and Ukrainian languages. The default locale is ```en``` and can be changed at ```application.locale```.

- Resources

  - Besides IP database and encryption keys, you need to set up some media files (pictures). This media is allowed to be used in development, however there may be issues with licensing, so they are currently git ignored. These include pictures under `resources/static/api/nbaapi/arenas`, `resources/static/api/nbaapi/players` and `resources/static/lang-icons`. For development, I will provide links where you will find them and download yourself.
  - Minimized scripts and everything related to that is also git ignored. This includes the main script under `resources/static/bundles/home`. You will have to build it and place it under this folder yourself. Currently, the React UI is only about that script, so should be no issues with that.

### Internationalization

Our project currently supports English and Ukrainian. We welcome contributions to add additional languages! All localization text files are located in the `resources/bundle` folder. If you'd like to contribute translations, please feel free to add a new language file following the existing format, and submit a pull request.


## Demo
### Signing up

### To join Score Track, follow these simple steps:

- Choose a Login Name:

  - Your login name should be between 5 and 15 characters.
  - Only letters, numbers, and underscores (_) are allowed.

- Create a Password:

  - Must be at least 8 characters long.
  - Include at least one digit, one lowercase letter, one uppercase letter, and one special character.
  - No whitespaces allowed.

- Confirm Your Password:

  - Enter the same password to ensure accuracy.

- Enter Your Email:

  - Provide a valid email address for account verification.

- Agree to the Terms of Service:

  - Confirm your agreement to the Score Track Terms of Service.

After completing the form, you'll receive an email with a temporary link to activate your account. Once activated, you can log in using your login name and password to start tracking your scores on Score Track!

![registration](https://github.com/IvanFromOdesa/score-track/assets/103036130/61b0e403-9f64-47be-ac36-700eb47c5dc7)


### Account activation

### Account Activation for Score Track

After signing up for Score Track, you will receive an email containing a temporary activation link. Follow these steps to activate your account:

- Check Your Email:

  - Look for an email from Score Track containing the activation link.

- Follow the Activation Link:

  - Click on the link within the email. Ensure you do this before the link expires.

- Account Activation:

  - Once you click the link, you will be redirected to the login page.
  - A message will indicate that your account has been successfully activated.

- Log In:

  - Use your login name and password to access your Score Track account and start enjoying our services!

![activation](https://github.com/IvanFromOdesa/score-track/assets/103036130/11ad4923-003a-4865-ae2f-a332d7ef25ab)


### Login

To log in into your account, enter the username and password that were provided during your registration.

![login](https://github.com/IvanFromOdesa/score-track/assets/103036130/319bd668-26a8-4c4f-8663-4b2ebfae9ce2)


### User profile

The public profile page on Score Track allows users to personalize their account with the following features:

- Profile Picture:

  - Upload and update your profile image.

- Profile Info:

  - Enter your first and last name.
  - Choose a public nickname.
  - Provide your date of birth (visible only to you).
  - Write a brief bio about yourself.

- Social Media Links:

  - Add links to your Instagram and Twitter (X) profiles.

- Sport Preferences:

  - Select your preferred sports to receive relevant data in your feed.

- Update Profile:

  - Click the "Update profile" button to save any changes made to your profile.

This page helps users to manage and customize their public profile information, making their Score Track experience more personalized and connected.

![profile](https://github.com/IvanFromOdesa/score-track/assets/103036130/805025ef-de1c-4041-853c-506c126a2201)


### Players leaderboard

The Players Leaderboard on Score Track provides a comprehensive view of top-performing players based on individual efficiency. Key features include:

- Season Leaders:

  - Displays the top players of the current season.
  - Users can filter players by different seasons and specific stats.

- Detailed Rankings:

  - Lists players with their respective teams and efficiency scores.
  - Allows users to easily compare player performances.

This leaderboard helps users stay informed about the top players and their performance metrics throughout the seasons.

![players](https://github.com/IvanFromOdesa/score-track/assets/103036130/afc48c05-9f03-4d75-8b0d-f5e9fba68ae6)


### Individual player's stats

The Player Stats webpage on Score Track offers a detailed overview of individual players, featuring:

- Player Information:

  - Name, height, weight, years of professional experience, college, team affiliation, birthday, and country of birth.

- Seasonal Stats:

  - Comprehensive statistics for each season, allowing users to track the player's performance over time.

This page provides an in-depth look at player profiles and their career stats, helping users analyze and follow their favorite players’ progress and achievements throughout their careers.

![p_stats_1](https://github.com/IvanFromOdesa/score-track/assets/103036130/f919f7c4-883d-4814-808f-8aefe6a9a755)

![p_stats_2](https://github.com/IvanFromOdesa/score-track/assets/103036130/8ecba35f-2770-4729-a19d-27bbe7fa63db)

![p_stats_3](https://github.com/IvanFromOdesa/score-track/assets/103036130/91b7b7e5-7854-4c20-a3a2-36342890c417)


### Teams

The Teams web page on Score Track provides a comprehensive overview of each team, featuring:

- Team Information:

  - Name and nickname.
  - Sport-specific details such as conference and division (e.g., for NBA teams).

- Team Description:

  - A brief description of the team.
  - Includes a link to the team's Wikipedia page for more detailed information.

- Seasonal Stats and Comparisons:

  - Detailed statistics for the team across different seasons.
  - Allows users to compare these stats with other teams for in-depth analysis.

- Export Options:

  - Users can export team statistics in various formats, including PDF and CSV.

This page serves as a valuable resource for fans and analysts, offering detailed team profiles, performance metrics, and easy data export options.

![teams_1](https://github.com/IvanFromOdesa/score-track/assets/103036130/f5e1e1f2-e926-4a27-b149-d72b8fa8b488)


TODO:
1. Enabling / disabling specific api from properties config.

Learn about Score-track [here](https://ivanfromodesa.github.io/score-track-landing/).
