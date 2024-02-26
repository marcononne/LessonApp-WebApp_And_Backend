-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Creato il: Giu 09, 2023 alle 18:50
-- Versione del server: 10.4.28-MariaDB
-- Versione PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_progetto`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `course`
--

CREATE TABLE `course` (
  `ID_Course` int(11) NOT NULL,
  `Title` varchar(50) NOT NULL,
  `Description` varchar(500) NOT NULL,
  `Active` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `course`
--

INSERT INTO `course` (`ID_Course`, `Title`, `Description`, `Active`) VALUES
(200, 'Algorithms and Data Structures', 'Problems and algorithms - Asyntotic notation and computational complexity - Sorting algorithms - Dynamic programming - Data structures - Trees - Graphs', 1),
(201, 'Mathematical Analysis', 'Functions, graphs and models - The concept of limit - Differential calculus - Approximate resolution of equations - Integrals - Numerical series\r\n', 1),
(202, 'Computer Architecture', 'Introduction to computer abstractions and technology - Information representation and Instruction sets - Digital logic and digital systems - RISC-V processors - Bus and I/O - Memory hierarchy', 1),
(203, 'Databases', 'Information systems and databases - The relational model - Relational algebra and algebra expressions - Relational calculus - Transactions - Redundancies and anomalies, functional dependencies and the theory of normalization - DBMS architeture', 1),
(204, 'Computability and Complexity', 'Turing Machines - Unsolvable problems - Recursive functions - Computability and programming languages - Complexity measures and classes - Temporal complexity classes - Spatial complexity classes - The classes P and NP\r\nNP-complete problems', 0),
(205, 'Matrix Calculus and Operational Research', 'Vectors and matrices, Basic operations, Convex sets and polyhedra - Solutions of a system of linear equations - Linear combinations, linear independence - Linear programming', 1),
(206, 'Management and Computer Law', 'Business model organization structure - Process set up strategy - Industry analysis market - Analysis business unit and business process management - ISPs\' liabilities - Computer Crimes and Digital Evidence -  Electronic document, signature and certified email - Intellectual property in a web environment - Privacy - E-commerce and web contracts', 1),
(207, 'Managing Innovation', 'Strategic mapping and analysis - Strategic approaches for innovation - New business model innovation -Customer journey - Opportunity discovery - Value proposition -Minimum Viable product - Lean test - Experiment - Ux/design and visualization', 0),
(208, 'Foundations of Probability and Statistics', 'Sample space and probability - Probabilistic models - Conditional probability - Discrete random variables - Probability mass function - Expectation - Variance -  Continuous random variables', 1),
(209, 'Physics', 'Gauss\' law - Electric field and electric potential - Equipotential surfaces - Electric capacitance of a conductor - Capacitors - Ohm\'s laws and Kirchhoff\'s laws. CR circuits - Magnets - Motion of an electric charge inside a magnetic field - Ampere\'s law. Gauss\' law for the magnetic field - Faraday-Henry\'s law - Ampere-Maxwell\'s law - Self-inductance - RL circuits', 1),
(210, 'Human-Computer Interaction', 'Human Computer Interaction - Interaction Design and User Experience - UX processes and rapid prototyping - Desktop and mobile web usability guidelines - Principles of information architecture - The WCAGs and AGID guidelines - User-centered design and evaluation methodologies - The evaluation of accessibility', 0),
(211, 'Human-Computer Interaction and Web Tecnologies', 'Best practices for the development of an Android application - Usage of the Flutter framework - Python -   Model View Controller pattern for web applications - Framework for MVC web-based applications (Vue.js)', 1),
(212, 'English I', 'Verb Patterns - Relative Pronouns - Present Simple - Adverbs of frequency - Present Continuous -  Phrasal verbs - Gerund - Simple Past - Simple Past Continuous -  Futures - Present Perfect - Past Perfect - Past Perfect Continuous -  Future Perfect - Future Perfect Continuous - Modals - Passive - Social English', 1),
(213, 'Programming Languages and Paradigms', 'Computing as rewriting -  Expressions and their types - Basic types - Design of functional programs - Recursion techniques - \r\nLists and first-order functions on lists - Proving correctness of functional programs using induction - Higher-order functions and\r\ntransformation patterns - Trees and general algebraic types', 0),
(214, 'Formal Languages and Compilers', 'Finite-state automata - Regular expressions - Properties of regular languages - Context-free grammars - Pushdown automata - Properties of context-free languages - Top-down parsing - Syntax-directed translation - Intermediate code generation', 1),
(215, 'Logic', 'Proof techniques - Basic set theory - Cardinality - The principle of induction - Formalization - Semantics of first order logic', 1),
(216, 'Logic for Computer Science', 'Deductive systems, models and interpretation, soundness and completeness - Resolution, BDD, SAT solvers.', 0),
(217, 'Discrete Mathematics', 'The language of set theory - Combinatorial calculus - Algebraic structures - Permutation groups', 1),
(218, 'Formal Methods in Computer Science', 'Equational reasoning and term rewriting - Natural deduction - Lambda calculus - Functions and data - Constructive logic - Lists and data structures - Verification of functional programs - Syntax of the language IMP - Big-step and small-step operational semantics - \r\nHoare logic - Relative completeness - Verification conditions - Separation logic', 0),
(219, 'Programming I', 'Differences between compilers and interpreters - Iterative and recursive algorithms - Language of reference: variables, basic data types and array, assignment, flow control, procedures and functions with parameters, model of memory management - Partial correctness, termination and some intuition about the notion of cost for an algorithm', 1),
(220, 'Programming II', 'Incapsulation, class definitions, creation and use of objects - Class specialization, inheritance - Relations among classes, polymorphism, partially defined classes, interfaces - Class generalization, parametric classes, generic types - Class invariants, assertions, exceptions and exception handling - Design and implementation of data lists, trees, stacks, queues - Collections and iterators', 1),
(221, 'Programming III', 'Event sources, event handlers, event-driven programming - Organization and use of graphical interfaces in Java - MVC architecture - Concurrent execution of statements - Threads in Java - Client-server architecture - Java sockets - Polymorphism and object transfer in Java - Modeling distributed execution of objects', 1),
(222, 'Computer Networks Advanced', 'Introduction to Internet and to computer networks - Application Layer -  Transport Layer - Network Layer - Link Layer and the Local Area Networks: links, access networks and local area networks - Wireless and Mobile Networks', 0),
(223, 'Computer Networks I', 'Introduction to Internet and to computer networks - Application Layer -  Transport Layer - Network Layer - Link Layer and the Local Area Networks: links, access networks and local area networks - Wireless and Mobile Networks', 1),
(224, 'Web Services', 'Architectures of Web applications: Web browser and Web server - MVC pattern for web applications - design and development of 3-tier Web applications based on MVC - HTML5 - CSS - JavaScript and AJAX -   Java Servlets - Java Database Connectivity - XML Schema, XPath, XML Parsers - Vue.js - PHP', 0),
(225, 'Computer and Network Security', 'Symmetric and asymmetric ciphers -  Collision resistant hash functions and birthday attacks - Symmetric authentication and digital signatures - LAN and WAN security - Availability and denial of service - Network security defenses and firewalls - Software security - Virtual private networks - Risk analysis in cybersecurity', 0),
(226, 'Information Systems', 'Introduction to Enterprise Information Systems - ERP systems - Integration with the client: CRM - Data Warehouse: data sources and ETL process; design of the system for data warehousing; data elaboration: reporting and DSS; data mining - Process-oriented Management of Enterprises - UML and BPMN', 1),
(227, 'Intelligent Systems', 'The course is an introduction to AI and covers the basic methodologies useful for understanding and designing an intelligent system.\r\nThe course is organized around three main topics: Automated problem solving - Knowledge representation and reasoning - Agents and machine learning', 1),
(228, 'Operating Systems', 'Introduction to Operating systems -  Processes - Threads - CPU Scheduling - Process syncronization - Central Memory - Virtual Memory -  File System Interface - File system Implementation - Secondary and Tertiary memory - C Language ', 1),
(229, 'History of Computer Science', 'The course provides an introduction to the history of computer science, with a special focus on the milestones that have contributed to shape its current aspect', 0),
(230, 'Development of Software Applications', 'Waterfall - Spiral - V-shaped -  Componend-based Development - Agile methodologies - Introduction to UML - Unified Process - Developing phases - Use cases - Domain Model -  Sequence System Diagrams -  Contracts - Logic Architecture and layer organization - DSD and DCD - Unit testing - Acceptance test - White and black box testing', 1),
(231, 'Web Technologies', 'Basic design and implementation of websites - Discussion of different navigation and organizational strategies - HTML5 - CSS - JavaScript - JSON - JQuery - PhP - \r\nBack-end data management - Emerging technologies', 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `lesson`
--

CREATE TABLE `lesson` (
  `ID_Lesson` int(11) NOT NULL,
  `User` varchar(40) NOT NULL,
  `Course` int(11) NOT NULL,
  `Teacher` int(11) NOT NULL,
  `Date` date NOT NULL,
  `Day` char(3) NOT NULL,
  `Hour` int(11) NOT NULL,
  `Status` varchar(10) NOT NULL COMMENT 'Active / Done / Deleted'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `lesson`
--

INSERT INTO `lesson` (`ID_Lesson`, `User`, `Course`, `Teacher`, `Date`, `Day`, `Hour`, `Status`) VALUES
(26, 'Happy_Feet', 205, 5025, '2023-06-09', 'FRI', 16, 'Deleted'),
(27, 'Happy_Feet', 227, 5051, '2023-07-06', 'THU', 18, 'Done'),
(28, 'Happy_Feet', 201, 5014, '2023-07-25', 'TUE', 15, 'Deleted'),
(29, 'Happy_Feet', 202, 5056, '2023-08-30', 'WED', 17, 'Active'),
(30, 'Ico', 230, 5048, '2023-06-29', 'THU', 16, 'Deleted'),
(31, 'Ico', 228, 5005, '2023-06-26', 'MON', 16, 'Done'),
(32, 'Ico', 226, 5060, '2023-06-21', 'WED', 16, 'Done'),
(33, 'Kinderino', 223, 5058, '2023-06-15', 'THU', 15, 'Done'),
(34, 'Kinderino', 221, 5001, '2023-06-14', 'WED', 16, 'Deleted'),
(35, 'Kinderino', 220, 5024, '2023-06-22', 'THU', 16, 'Done'),
(36, 'Kinderino', 221, 5000, '2023-07-06', 'THU', 15, 'Done'),
(37, 'Kinderino', 211, 5000, '2023-07-06', 'THU', 17, 'Deleted'),
(38, 'Kinderino', 212, 5040, '2023-06-14', 'WED', 17, 'Done'),
(39, 'Kinderino', 209, 5033, '2023-08-10', 'THU', 18, 'Deleted'),
(43, 'Kinderino ', 200, 5013, '2023-07-19', 'WED', 16, 'Deleted'),
(52, 'Happy_Feet', 214, 5003, '2023-06-30', 'FRI', 18, 'Deleted'),
(53, 'Happy_Feet', 201, 5017, '2023-07-07', 'FRI', 17, 'Deleted'),
(79, 'Ico ', 201, 5014, '2023-06-27', 'TUE', 15, 'Done'),
(80, 'Ico ', 201, 5014, '2023-06-28', 'WED', 17, 'Deleted'),
(81, 'Ico ', 201, 5017, '2023-06-30', 'FRI', 15, 'Done'),
(83, 'Ico ', 202, 5056, '2023-06-20', 'TUE', 18, 'Done'),
(84, 'Ico ', 206, 5027, '2023-08-17', 'THU', 15, 'Deleted'),
(85, 'Ico ', 208, 5031, '2023-09-22', 'FRI', 15, 'Deleted'),
(86, 'Ico ', 205, 5026, '2023-08-18', 'FRI', 15, 'Deleted'),
(87, 'Ico ', 227, 5051, '2023-08-18', 'FRI', 16, 'Deleted'),
(88, 'Ico ', 202, 5056, '2023-06-15', 'THU', 17, 'Done'),
(89, 'Ico ', 228, 5005, '2023-08-30', 'WED', 17, 'Deleted'),
(96, 'Ico ', 219, 5052, '2023-08-22', 'TUE', 17, 'Done'),
(97, 'Ico ', 201, 5017, '2023-08-22', 'TUE', 16, 'Deleted'),
(98, 'Ico ', 206, 5027, '2023-06-16', 'FRI', 17, 'Deleted'),
(99, 'Bimba ', 201, 5017, '2023-06-16', 'FRI', 16, 'Deleted'),
(100, 'Bimba ', 206, 5027, '2023-06-26', 'MON', 17, 'Done'),
(101, 'Bimba ', 209, 5033, '2023-06-30', 'FRI', 15, 'Active'),
(107, 'Happy_Feet ', 208, 5031, '2023-08-22', 'TUE', 16, 'Deleted'),
(110, 'Bimba ', 211, 5037, '2023-07-20', 'THU', 17, 'Active');

-- --------------------------------------------------------

--
-- Struttura della tabella `teacher`
--

CREATE TABLE `teacher` (
  `ID_Teacher` int(11) NOT NULL,
  `Name` varchar(20) NOT NULL,
  `Surname` varchar(20) NOT NULL,
  `Active` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `teacher`
--

INSERT INTO `teacher` (`ID_Teacher`, `Name`, `Surname`, `Active`) VALUES
(5000, 'Liliana', 'Ardissono', 1),
(5001, 'Roberto', 'Esposito', 1),
(5002, 'Luca', 'Padovani', 0),
(5003, 'Jeremy', 'Sproston', 0),
(5004, 'Enrico', 'Bini', 1),
(5005, 'Claudio', 'Schifanella', 1),
(5006, 'Ugo', 'DeLiguoro', 1),
(5011, 'Alessandro', 'Andretta', 0),
(5012, 'Andras', 'Horvath', 0),
(5013, 'Gianluca', 'Pozzato', 1),
(5014, 'Vivina', 'Barutello', 1),
(5015, 'Stefano', 'Vita', 0),
(5016, 'Joerg', 'Seiler', 0),
(5017, 'Alberto', 'Boscaggin', 1),
(5018, 'Luca', 'MottoRoss', 1),
(5019, 'Maurizio', 'Lucenteforte', 0),
(5020, 'Marco', 'Aldinucci', 0),
(5021, 'Ruggero', 'Pensa', 1),
(5022, 'Fabiana', 'Vernero', 1),
(5023, 'Noemi', 'Mauro', 0),
(5024, 'Stefano', 'Berardi', 1),
(5025, 'Roberto', 'Aringhieri', 1),
(5026, 'Andrea', 'Grosso', 1),
(5027, 'Lea', 'Iaia', 1),
(5028, 'Camillo', 'Sacchetto', 1),
(5029, 'Marco', 'Pironti', 0),
(5030, 'Fabio', 'Montalcini', 0),
(5031, 'Roberta', 'Sirovich', 1),
(5032, 'Maria', 'Giraudo', 0),
(5033, 'Igor', 'Pesando', 1),
(5034, 'Ernesto', 'Migliore', 0),
(5035, 'Martino', 'Gagliardi', 0),
(5036, 'Cristina', 'Gena', 1),
(5037, 'Marino', 'Segnan', 1),
(5038, 'Matteo', 'Viale', 1),
(5039, 'Viviana', 'Bono', 1),
(5040, 'Valentina', 'Gliozzi', 1),
(5041, 'Catherine', 'Merrett', 0),
(5042, 'Luigi', 'Di Caro', 0),
(5043, 'Luca', 'Paolini', 0),
(5044, 'Andrea', 'Mori', 1),
(5045, 'Yu', 'Chen', 1),
(5046, 'Lea', 'Terraccini', 0),
(5047, 'Cristina', 'Bertone', 0),
(5048, 'Sara', 'Capecchi', 1),
(5049, 'Luca', 'Roversi', 1),
(5050, 'Felice', 'Cardone', 0),
(5051, 'Cristina', 'Baroglio', 1),
(5052, 'Robert', 'Birke', 1),
(5053, 'Ferruccio', 'Damiani', 0),
(5054, 'Gianluca', 'Torta', 1),
(5055, 'Ciro', 'Cattuto', 0),
(5056, 'Michele', 'Garetto', 1),
(5057, 'Matteo', 'Sereno', 0),
(5058, 'Marco', 'Botta', 1),
(5059, 'Francesco', 'Bergadano', 1),
(5060, 'Roberto', 'Micalizio', 1),
(5061, 'Massimiliano', 'De Pierro', 0),
(5062, 'Daniele', 'Gunetti', 0),
(5063, 'Mirko', 'Polato', 1),
(5064, 'Matteo', 'Baldoni', 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `teaching`
--

CREATE TABLE `teaching` (
  `Course` int(11) NOT NULL,
  `Teacher` int(11) NOT NULL,
  `Active` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `teaching`
--

INSERT INTO `teaching` (`Course`, `Teacher`, `Active`) VALUES
(200, 5006, 1),
(200, 5012, 0),
(200, 5013, 1),
(200, 5060, 1),
(201, 5014, 1),
(201, 5015, 0),
(201, 5016, 0),
(201, 5017, 1),
(202, 5005, 1),
(202, 5019, 0),
(202, 5056, 1),
(203, 5021, 1),
(203, 5022, 1),
(203, 5023, 0),
(204, 5024, 0),
(205, 5025, 1),
(205, 5026, 1),
(206, 5027, 1),
(206, 5028, 1),
(206, 5029, 0),
(206, 5030, 0),
(207, 5029, 0),
(208, 5031, 1),
(208, 5032, 0),
(209, 5033, 1),
(209, 5034, 0),
(209, 5035, 0),
(210, 5036, 0),
(210, 5037, 0),
(211, 5000, 1),
(211, 5036, 1),
(211, 5037, 1),
(212, 5037, 1),
(212, 5039, 1),
(212, 5040, 1),
(212, 5041, 0),
(213, 5039, 0),
(214, 5002, 0),
(214, 5003, 0),
(214, 5013, 0),
(214, 5042, 0),
(215, 5011, 0),
(215, 5018, 1),
(215, 5038, 1),
(216, 5043, 0),
(217, 5044, 1),
(217, 5045, 1),
(217, 5046, 0),
(217, 5047, 0),
(218, 5006, 0),
(219, 5042, 0),
(219, 5049, 1),
(219, 5050, 0),
(219, 5051, 1),
(219, 5052, 1),
(220, 5024, 1),
(220, 5039, 0),
(220, 5053, 0),
(220, 5054, 1),
(221, 5000, 1),
(221, 5001, 1),
(222, 5056, 0),
(222, 5057, 0),
(223, 5058, 1),
(224, 5000, 1),
(225, 5059, 0),
(226, 5060, 1),
(227, 5051, 1),
(228, 5004, 1),
(228, 5005, 1),
(228, 5051, 1),
(228, 5061, 0),
(228, 5062, 0),
(229, 5050, 0),
(229, 5062, 1),
(230, 5048, 1),
(230, 5052, 1),
(230, 5053, 0),
(230, 5063, 1),
(230, 5064, 0),
(231, 5058, 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `user`
--

CREATE TABLE `user` (
  `Username` varchar(40) NOT NULL,
  `Password` char(64) NOT NULL,
  `Name` varchar(20) NOT NULL,
  `Gender` varchar(30) NOT NULL COMMENT 'Male / Female / I prefer not to specify',
  `Role` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `user`
--

INSERT INTO `user` (`Username`, `Password`, `Name`, `Gender`, `Role`) VALUES
('Bimba', '2C44EC8705859D504502F5B0A1724175E4F3280E05775CAD4B0BBD10B3917EDB', 'Camilla', 'Female', 'Client'),
('Happy_Feet', '6071926C520CD1A6E2A16BCC0C5E64316BBCF558556D29070B9500079B13E202', 'Ludovica', 'Female', 'Client'),
('Ico', '29A8F3DB6120BB27D8135839B8A24E9DD3D37861D92B2129AA559EFBACC11F33', 'Federico', 'Male', 'Client'),
('Kinderino', 'B14D5140AF6988F3ADC7044A374107D7F8C7055EAC489DD9F2412A9C9A31AB0C', 'Gaetano', 'Male', 'Client'),
('Nenno', 'E7CF3EF4F17C3999A94F2C6F612E8A888E5B1026878E4E19398B23BD38EC221A', 'Marco', 'Male', 'Admin'),
('Piantina', '847E0B831A85EECEF076C9E1B06E1D378542F072FB5B3153722CD4FBABDA29AC', 'Erminio', 'Male', 'Admin'),
('Topo_Gigio', 'A72EE914E4F5DF45302D921BE40CCA1D84F59FDB10AE8DAE77E54C2F9F4A9AF7', 'Catalina', 'Female', 'Admin');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`ID_Course`);

--
-- Indici per le tabelle `lesson`
--
ALTER TABLE `lesson`
  ADD PRIMARY KEY (`ID_Lesson`),
  ADD KEY `fk_Lesson_User` (`User`),
  ADD KEY `fk_Lesson_Course` (`Course`),
  ADD KEY `fk_Lesson_Teacher` (`Teacher`);

--
-- Indici per le tabelle `teacher`
--
ALTER TABLE `teacher`
  ADD PRIMARY KEY (`ID_Teacher`);

--
-- Indici per le tabelle `teaching`
--
ALTER TABLE `teaching`
  ADD PRIMARY KEY (`Course`,`Teacher`),
  ADD KEY `fk_Teaching_Teacher` (`Teacher`);

--
-- Indici per le tabelle `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`Username`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `course`
--
ALTER TABLE `course`
  MODIFY `ID_Course` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=234;

--
-- AUTO_INCREMENT per la tabella `lesson`
--
ALTER TABLE `lesson`
  MODIFY `ID_Lesson` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=111;

--
-- AUTO_INCREMENT per la tabella `teacher`
--
ALTER TABLE `teacher`
  MODIFY `ID_Teacher` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5067;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `lesson`
--
ALTER TABLE `lesson`
  ADD CONSTRAINT `fk_Lesson_Course` FOREIGN KEY (`Course`) REFERENCES `course` (`ID_Course`),
  ADD CONSTRAINT `fk_Lesson_Teacher` FOREIGN KEY (`Teacher`) REFERENCES `teacher` (`ID_Teacher`),
  ADD CONSTRAINT `fk_Lesson_User` FOREIGN KEY (`User`) REFERENCES `user` (`Username`);

--
-- Limiti per la tabella `teaching`
--
ALTER TABLE `teaching`
  ADD CONSTRAINT `fk_Teaching_Course` FOREIGN KEY (`Course`) REFERENCES `course` (`ID_Course`),
  ADD CONSTRAINT `fk_Teaching_Teacher` FOREIGN KEY (`Teacher`) REFERENCES `teacher` (`ID_Teacher`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
