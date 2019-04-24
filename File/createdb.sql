CREATE TABLE Movies (
mid INTEGER,
title VARCHAR2(255),
myear INTEGER,
rtRating NUMBER,
rtNo INTEGER,
PRIMARY KEY (mid));

CREATE TABLE Movie_genres (
mid INTEGER,
genres VARCHAR2(100),
PRIMARY KEY (mid,genres),
FOREIGN KEY (mid) REFERENCES Movies ON DELETE CASCADE);

CREATE TABLE Movie_countries (
mid INTEGER,
country VARCHAR2(100),
PRIMARY KEY (mid),
FOREIGN KEY (mid) REFERENCES Movies ON DELETE CASCADE);

CREATE TABLE Tags (
tid INTEGER,
tvalue VARCHAR2(255) NOT NULL,
PRIMARY KEY (tid));

CREATE TABLE Movie_tags (
mid INTEGER,
tid INTEGER,
tag_weight INTEGER NOT NULL,
PRIMARY KEY (mid,tid),
FOREIGN KEY (mid) REFERENCES Movies,
FOREIGN KEY (tid) REFERENCES Tags);

CREATE TABLE Actors (
mid INTEGER NOT NULL,
actorId VARCHAR2(80),
actor_name VARCHAR2(80),
PRIMARY KEY (mid,actorId),
FOREIGN KEY (mid) REFERENCES Movies ON DELETE CASCADE);

CREATE TABLE Directors (
mid INTEGER NOT NULL,
directorId VARCHAR2(80),
director_name VARCHAR2(80),
PRIMARY KEY(mid,directorId),
FOREIGN KEY(mid) REFERENCES Movies ON DELETE CASCADE);

CREATE TABLE Usertagmovies (
userid INTEGER,
mid INTEGER,
tid INTEGER,
PRIMARY KEY (userid,mid,tid),
FOREIGN KEY (mid) REFERENCES Movies ON DELETE CASCADE,
FOREIGN KEY (tid) REFERENCES Tags
);

CREATE INDEX MovieYearIndex ON Movies (myear);
CREATE INDEX MidMovieTagIndex ON Movie_tags (mid);
CREATE INDEX TidMovieTagIndex ON Movie_tags (tid);
CREATE INDEX TWeightMovieTagIndex ON Movie_tags (tag_weight);

CREATE INDEX MovieidUserTagIndex ON Usertagmovies (mid);
CREATE INDEX TagidUserTagIndex ON Usertagmovies (tid);
CREATE INDEX UseridUserTagIndex ON Usertagmovies (userid);
CREATE INDEX MovieGenreIndex ON Movie_genres (genres);
CREATE INDEX MovieCountryIndex ON Movie_countries (country);
CREATE INDEX TagValueIndex ON Tags(tvalue);
CREATE INDEX ActorNameIndex ON Actors (actor_name);
CREATE INDEX DirectorNameIndex ON Directors (director_name);