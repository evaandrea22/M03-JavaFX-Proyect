-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 03-06-2022 a las 00:14:21
-- Versión del servidor: 10.4.19-MariaDB
-- Versión de PHP: 8.0.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `filmoteca`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `directores`
--

CREATE TABLE `directores` (
  `id_director` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `flag` varchar(255) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `directores`
--

INSERT INTO `directores` (`id_director`, `nombre`, `flag`, `timestamp`) VALUES
(1, 'James Cameron', NULL, '2022-06-02 22:04:54'),
(2, 'Jean Cocteau', NULL, '2022-06-02 22:04:54'),
(3, 'John Lasseter', NULL, '2022-06-02 22:04:54'),
(4, 'Ridley Scott', NULL, '2022-06-02 22:04:54');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `peliculas`
--

CREATE TABLE `peliculas` (
  `id_pelicula` int(11) NOT NULL,
  `titulo` varchar(50) NOT NULL,
  `fechaEstreno` date NOT NULL,
  `duracion` int(11) NOT NULL,
  `flag` varchar(255) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `peliculas`
--

INSERT INTO `peliculas` (`id_pelicula`, `titulo`, `fechaEstreno`, `duracion`, `flag`, `timestamp`) VALUES
(9, 'La bella y la bestia', '2022-06-30', 120, NULL, '2022-06-02 22:04:19'),
(10, 'Terminator 2', '2022-07-01', 125, NULL, '2022-06-02 22:04:19'),
(11, 'Toy Story', '2022-08-12', 130, NULL, '2022-06-02 22:04:19'),
(12, 'Gladiator', '2022-09-20', 140, NULL, '2022-06-02 22:04:19');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `peliculas_directores`
--

CREATE TABLE `peliculas_directores` (
  `fk_director` int(11) NOT NULL,
  `fk_pelicula` int(11) NOT NULL,
  `flag` varchar(255) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `peliculas_directores`
--

INSERT INTO `peliculas_directores` (`fk_director`, `fk_pelicula`, `flag`, `timestamp`) VALUES
(1, 10, NULL, '2022-06-02 22:08:46'),
(2, 9, NULL, '2022-06-02 22:08:46'),
(3, 11, NULL, '2022-06-02 22:08:46'),
(4, 12, NULL, '2022-06-02 22:08:46');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `directores`
--
ALTER TABLE `directores`
  ADD PRIMARY KEY (`id_director`);

--
-- Indices de la tabla `peliculas`
--
ALTER TABLE `peliculas`
  ADD PRIMARY KEY (`id_pelicula`);

--
-- Indices de la tabla `peliculas_directores`
--
ALTER TABLE `peliculas_directores`
  ADD KEY `fk_director` (`fk_director`),
  ADD KEY `fk_pelicula` (`fk_pelicula`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `directores`
--
ALTER TABLE `directores`
  MODIFY `id_director` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `peliculas`
--
ALTER TABLE `peliculas`
  MODIFY `id_pelicula` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `peliculas_directores`
--
ALTER TABLE `peliculas_directores`
  ADD CONSTRAINT `peliculas_directores_ibfk_1` FOREIGN KEY (`fk_director`) REFERENCES `directores` (`id_director`),
  ADD CONSTRAINT `peliculas_directores_ibfk_2` FOREIGN KEY (`fk_pelicula`) REFERENCES `peliculas` (`id_pelicula`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
