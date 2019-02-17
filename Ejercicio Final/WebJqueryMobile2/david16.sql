-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 27-09-2017 a las 19:32:41
-- Versión del servidor: 10.1.16-MariaDB
-- Versión de PHP: 5.6.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `david16`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `brands`
--

CREATE TABLE `brands` (
  `brand_id` int(100) NOT NULL,
  `brand_title` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `brands`
--

INSERT INTO `brands` (`brand_id`, `brand_title`) VALUES
(1, 'HP'),
(2, 'Samsung'),
(3, 'Apple'),
(4, 'Sony'),
(5, 'LG'),
(6, 'Biba'),
(7, 'Flying Machine'),
(8, 'Nike'),
(9, 'Adidas'),
(10, 'Kidzee'),
(11, 'Ikea'),
(12, 'Philips');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cart`
--

CREATE TABLE `cart` (
  `id` int(10) NOT NULL,
  `p_id` int(10) NOT NULL,
  `ip_add` varchar(250) NOT NULL,
  `user_id` int(10) NOT NULL,
  `product_title` varchar(100) NOT NULL,
  `product_image` varchar(300) NOT NULL,
  `qty` int(100) NOT NULL,
  `price` int(100) NOT NULL,
  `total_amount` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cart`
--

INSERT INTO `cart` (`id`, `p_id`, `ip_add`, `user_id`, `product_title`, `product_image`, `qty`, `price`, `total_amount`) VALUES
(79, 11, '0.0.0.0', 2, 'Baby Shirt', 'babyshirt.JPG', 1, 500, 500),
(80, 2, '0.0.0.0', 2, 'iPhone 5s', 'iphonemobile.JPG', 1, 25000, 25000);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categories`
--

CREATE TABLE `categories` (
  `cat_id` int(11) NOT NULL,
  `cat_title` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `categories`
--

INSERT INTO `categories` (`cat_id`, `cat_title`) VALUES
(1, 'Electronics'),
(2, 'Ladies Wear'),
(3, 'Mens Wear'),
(4, 'Kids Wear'),
(5, 'Home Appliances'),
(6, 'Sports');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comentario_foro`
--

CREATE TABLE `comentario_foro` (
  `id_comentario` int(11) NOT NULL,
  `id_tema` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `comentario` text NOT NULL,
  `fecha` date NOT NULL,
  `activo` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `comentario_foro`
--

INSERT INTO `comentario_foro` (`id_comentario`, `id_tema`, `id_usuario`, `comentario`, `fecha`, `activo`) VALUES
(1, 1, 3, '<p>que bien!!!</p>', '2015-04-17', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `customer_order`
--

CREATE TABLE `customer_order` (
  `id` int(100) NOT NULL,
  `uid` int(100) NOT NULL,
  `pid` int(100) NOT NULL,
  `p_name` varchar(255) NOT NULL,
  `p_price` int(100) NOT NULL,
  `p_qty` int(100) NOT NULL,
  `p_status` varchar(100) NOT NULL,
  `tr_id` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `customer_order`
--

INSERT INTO `customer_order` (`id`, `uid`, `pid`, `p_name`, `p_price`, `p_qty`, `p_status`, `tr_id`) VALUES
(30, 2, 6, 'LG Aqua 2', 15000, 1, 'CONFIRMED', '15179'),
(31, 2, 15, 'Football Shoes', 2500, 1, 'CONFIRMED', '15179'),
(32, 2, 16, 'Football', 600, 1, 'CONFIRMED', '15179');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estadisticas`
--

CREATE TABLE `estadisticas` (
  `id_estadistica` int(11) NOT NULL,
  `id_tema` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `fecha` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `foro_categoria`
--

CREATE TABLE `foro_categoria` (
  `id_forocategoria` int(11) NOT NULL,
  `categoria` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `foro_categoria`
--

INSERT INTO `foro_categoria` (`id_forocategoria`, `categoria`) VALUES
(1, 'Deportes'),
(2, 'Musica');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `foro_foro`
--

CREATE TABLE `foro_foro` (
  `id_foro` int(11) NOT NULL,
  `id_forocategoria` int(11) NOT NULL,
  `foro` varchar(250) NOT NULL,
  `descripcion` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `foro_foro`
--

INSERT INTO `foro_foro` (`id_foro`, `id_forocategoria`, `foro`, `descripcion`) VALUES
(1, 1, 'Futbol', 'Futbol'),
(2, 0, 'mundiales', 'mundiales'),
(3, 0, 'Liguillas', 'Liguillas'),
(4, 0, 'torneos', 'torneos'),
(5, 0, 'Lucha libre', 'Lucha libre'),
(6, 1, 'Lucha libre', 'Lucha libre'),
(7, 0, 'wwe', 'wwe'),
(8, 0, 'aaa', 'aaa'),
(10, 2, 'Pop', 'Pop'),
(11, 2, 'Rock', 'Rock');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `foro_subforos`
--

CREATE TABLE `foro_subforos` (
  `id_subforo` int(11) NOT NULL,
  `id_foro` int(11) NOT NULL,
  `subforo` varchar(250) NOT NULL,
  `descripcion` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `foro_subforos`
--

INSERT INTO `foro_subforos` (`id_subforo`, `id_foro`, `subforo`, `descripcion`) VALUES
(1, 1, 'mundiales', 'mundiales'),
(2, 1, 'Liguillas', 'Liguillas'),
(3, 1, 'torneos', 'torneos'),
(5, 6, 'wwe', 'wwe'),
(6, 6, 'aaa', 'aaa');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `foro_temas`
--

CREATE TABLE `foro_temas` (
  `id_tema` int(11) NOT NULL,
  `id_foro` int(11) NOT NULL,
  `id_subforo` int(11) NOT NULL,
  `titulo` varchar(250) NOT NULL,
  `contenido` text NOT NULL,
  `fecha` date NOT NULL,
  `id_usuario` int(5) NOT NULL,
  `activo` int(1) NOT NULL,
  `hits` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `foro_temas`
--

INSERT INTO `foro_temas` (`id_tema`, `id_foro`, `id_subforo`, `titulo`, `contenido`, `fecha`, `id_usuario`, `activo`, `hits`) VALUES
(1, 10, 0, 'Nuevo disco de...', '<p>Despues de mucho esperar el grupo x a lanzado su nuevo disco con 12 canciones ineditas...</p>', '2015-04-17', 3, 1, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `products`
--

CREATE TABLE `products` (
  `product_id` int(100) NOT NULL,
  `product_cat` varchar(100) NOT NULL,
  `product_brand` varchar(100) NOT NULL,
  `product_title` varchar(50) NOT NULL,
  `product_price` int(100) NOT NULL,
  `product_desc` text NOT NULL,
  `product_image` text NOT NULL,
  `product_keywords` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `products`
--

INSERT INTO `products` (`product_id`, `product_cat`, `product_brand`, `product_title`, `product_price`, `product_desc`, `product_image`, `product_keywords`) VALUES
(1, '1', '2', 'Samsung Duos 2', 5000, 'Samsung Duos 2 mobile phone', 'samsungduos.JPG', 'samsung mobile electronics'),
(2, '1', '3', 'iPhone 5s', 25000, 'iPhone mobile ', 'iphonemobile.JPG', 'apple iphone mobile electronics'),
(3, '1', '3', 'iPad', 30000, 'iPad tablet for use', 'iPad.jpg', 'apple ipad tablet'),
(4, '1', '2', 'Samsung Tab', 10000, 'samsung tablet for home use', 'samsungtab.JPG', 'samsung tablet electronics'),
(5, '1', '4', 'Sony Vaio Laptop', 25000, 'Vaio Laptop', 'vaio.JPG', 'sony laptop vaio'),
(6, '1', '5', 'LG Aqua 2', 15000, 'LG aqua mobile phone all featured', 'lgaqua.JPG', 'lg mobile phone aqua'),
(7, '2', '6', 'Draped Lehenga', 1500, 'Matching Lehenga', 'lehenga.JPG', 'lehenga biba'),
(8, '2', '6', 'SIlk Saree', 1000, 'Pure Silk Saree', 'saree.JPG', 'biba saree'),
(9, '3', '7', 'T-Shirt', 700, 'T-Shirt for summer', 'tshirt.JPG', 'flying machine tshirt'),
(10, '3', '7', 'FM Jeans', 1800, 'Jeans for the ones who do', 'jeans.JPG', 'flying machine jeans'),
(11, '4', '10', 'Baby Shirt', 500, 'Shirt for the babies', 'babyshirt.JPG', 'kids shirt kidzee'),
(12, '4', '10', 'Kids Jeans', 800, 'Jeans for kids', 'kidsjeans.JPG', 'kids jeans kidzee'),
(13, '5', '11', 'Computer Table', 2000, 'Table for computer', 'computertable.JPG', 'computer table ikea '),
(14, '5', '12', 'Trimmer', 1500, 'Trimmer by Philips', 'philipstrimmer.JPG', 'philips trimmer'),
(15, '6', '8', 'Football Shoes', 2500, 'Shoes to play football by Nike', 'nikeshoes.JPG', 'nike shoes football'),
(16, '6', '9', 'Football', 600, 'Football by Adidas', 'adidasfootball.JPG', 'football adidas');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE `users` (
  `uid` int(11) NOT NULL,
  `username` varchar(30) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(70) DEFAULT NULL,
  `fechaderegistro` date NOT NULL,
  `ultimoacceso` date NOT NULL,
  `activo` int(2) NOT NULL,
  `avatar` varchar(200) NOT NULL,
  `mobile` varchar(10) NOT NULL,
  `address1` varchar(300) NOT NULL,
  `address2` varchar(300) NOT NULL,
  `numtarjeta` int(30) NOT NULL,
  `CV` int(3) NOT NULL,
  `fechacaducidad` varchar(30) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cart`
--
ALTER TABLE `cart`
  ADD KEY `user_id` (`user_id`),
  ADD KEY `p_id` (`p_id`);

--
-- Indices de la tabla `comentario_foro`
--
ALTER TABLE `comentario_foro`
  ADD PRIMARY KEY (`id_comentario`),
  ADD KEY `id_usuario` (`id_usuario`),
  ADD KEY `id_usuario_2` (`id_usuario`);

--
-- Indices de la tabla `estadisticas`
--
ALTER TABLE `estadisticas`
  ADD PRIMARY KEY (`id_estadistica`);

--
-- Indices de la tabla `foro_categoria`
--
ALTER TABLE `foro_categoria`
  ADD PRIMARY KEY (`id_forocategoria`);

--
-- Indices de la tabla `foro_foro`
--
ALTER TABLE `foro_foro`
  ADD PRIMARY KEY (`id_foro`);

--
-- Indices de la tabla `foro_subforos`
--
ALTER TABLE `foro_subforos`
  ADD PRIMARY KEY (`id_subforo`);

--
-- Indices de la tabla `foro_temas`
--
ALTER TABLE `foro_temas`
  ADD PRIMARY KEY (`id_tema`);

--
-- Indices de la tabla `products`
--
ALTER TABLE `products`
  ADD KEY `product_id` (`product_id`);

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`uid`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `comentario_foro`
--
ALTER TABLE `comentario_foro`
  MODIFY `id_comentario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `estadisticas`
--
ALTER TABLE `estadisticas`
  MODIFY `id_estadistica` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `foro_categoria`
--
ALTER TABLE `foro_categoria`
  MODIFY `id_forocategoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `foro_foro`
--
ALTER TABLE `foro_foro`
  MODIFY `id_foro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT de la tabla `foro_subforos`
--
ALTER TABLE `foro_subforos`
  MODIFY `id_subforo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT de la tabla `foro_temas`
--
ALTER TABLE `foro_temas`
  MODIFY `id_tema` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `users`
--
ALTER TABLE `users`
  MODIFY `uid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
