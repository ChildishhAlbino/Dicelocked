-- phpMyAdmin SQL Dump
-- version 4.8.0.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jun 07, 2018 at 03:49 PM
-- Server version: 10.1.32-MariaDB
-- PHP Version: 7.2.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dicelocked`
--

-- --------------------------------------------------------

--
-- Table structure for table `players`
--

CREATE TABLE `players` (
  `RecordID` int(11) NOT NULL,
  `PlayerName` varchar(25) NOT NULL,
  `MatchesWon` int(11) NOT NULL DEFAULT '0',
  `MatchesPlayed` int(11) NOT NULL DEFAULT '0',
  `WinPer` float NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `players`
--

INSERT INTO `players` (`RecordID`, `PlayerName`, `MatchesWon`, `MatchesPlayed`, `WinPer`) VALUES
(23, 'bino', 0, 0, 0),
(39, 'admin', 0, 0, 0),
(40, 'JackaCracka', 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `user_login`
--

CREATE TABLE `user_login` (
  `RecordID` int(11) NOT NULL,
  `Username` varchar(25) NOT NULL,
  `PasswordHash` varchar(66) NOT NULL,
  `User_Since` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Player_ID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_login`
--

INSERT INTO `user_login` (`RecordID`, `Username`, `PasswordHash`, `User_Since`, `Player_ID`) VALUES
(6, 'Bino', '6f30ea013fec908628192e919275e6a8b316e5b924afd8b85de6b01c416cc8b9', '2018-06-07 19:18:40', 23),
(18, 'admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '2018-06-07 23:40:55', 39),
(19, 'Jack', '189f40034be7a199f1fa9891668ee3ab6049f82d38c68be70f596eab2e1857b7', '2018-06-07 23:47:22', 40);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `players`
--
ALTER TABLE `players`
  ADD PRIMARY KEY (`RecordID`),
  ADD UNIQUE KEY `PlayerName` (`PlayerName`);

--
-- Indexes for table `user_login`
--
ALTER TABLE `user_login`
  ADD PRIMARY KEY (`RecordID`),
  ADD UNIQUE KEY `Username` (`Username`),
  ADD UNIQUE KEY `Username_2` (`Username`),
  ADD UNIQUE KEY `Player ID` (`Player_ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `players`
--
ALTER TABLE `players`
  MODIFY `RecordID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT for table `user_login`
--
ALTER TABLE `user_login`
  MODIFY `RecordID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
