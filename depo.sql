-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Anamakine: 127.0.0.1
-- Üretim Zamanı: 04 Eyl 2021, 18:45:08
-- Sunucu sürümü: 10.4.20-MariaDB
-- PHP Sürümü: 7.3.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Veritabanı: `depo`
--

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `admin`
--

CREATE TABLE `admin` (
  `id` int(32) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tablo döküm verisi `admin`
--

INSERT INTO `admin` (`id`, `email`, `password`, `name`) VALUES
(1, 'demet@mail.com', '827ccb0eea8a706c4c34a16891f84e7b', 'Demet Koçhan'),
(2, 'veli@mail.com', '827ccb0eea8a706c4c34a16891f84e7b', 'Veli Bilir');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `boxaction`
--

CREATE TABLE `boxaction` (
  `box_id` int(11) NOT NULL,
  `box_count` int(11) NOT NULL,
  `box_customer_id` int(11) NOT NULL,
  `box_product_id` int(11) NOT NULL,
  `box_receipt` int(11) NOT NULL,
  `box_status` int(11) NOT NULL,
  `product_pro_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tablo döküm verisi `boxaction`
--

INSERT INTO `boxaction` (`box_id`, `box_count`, `box_customer_id`, `box_product_id`, `box_receipt`, `box_status`, `product_pro_id`) VALUES
(1, 1, 1, 4, 1, 1, NULL),
(4, 1, 3, 3, 3, 1, NULL),
(5, 1, 3, 11, 3, 1, NULL),
(6, 3, 2, 2, 2, 1, NULL),
(7, 1, 2, 4, 2, 1, NULL),
(9, 1, 1, 3, 1, 1, NULL),
(12, 2, 3, 1, 4, 1, NULL),
(13, 2, 3, 1, 4, 1, NULL),
(14, 3, 3, 1, 6, 1, NULL),
(15, 1, 1, 1, 1, 1, NULL),
(16, 2, 1, 1, 10, 1, NULL),
(17, 1, 1, 1, 11, 1, NULL);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `boxcustomerproduct`
--

CREATE TABLE `boxcustomerproduct` (
  `box_id` int(11) NOT NULL,
  `cu_name` varchar(255) DEFAULT NULL,
  `cu_surname` varchar(255) DEFAULT NULL,
  `pro_sale_price` int(11) NOT NULL,
  `pro_title` varchar(255) DEFAULT NULL,
  `box_count` int(11) NOT NULL,
  `box_receipt` int(11) NOT NULL,
  `pay_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `customer`
--

CREATE TABLE `customer` (
  `cu_id` int(11) NOT NULL,
  `cu_address` varchar(500) DEFAULT NULL,
  `cu_code` bigint(20) NOT NULL,
  `cu_company_title` varchar(255) DEFAULT NULL,
  `cu_email` varchar(500) DEFAULT NULL,
  `cu_mobile` varchar(255) DEFAULT NULL,
  `cu_name` varchar(255) DEFAULT NULL,
  `cu_password` varchar(32) DEFAULT NULL,
  `cu_phone` varchar(255) DEFAULT NULL,
  `cu_status` int(11) NOT NULL,
  `cu_surname` varchar(255) DEFAULT NULL,
  `cu_tax_administration` varchar(255) DEFAULT NULL,
  `cu_tax_number` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tablo döküm verisi `customer`
--

INSERT INTO `customer` (`cu_id`, `cu_address`, `cu_code`, `cu_company_title`, `cu_email`, `cu_mobile`, `cu_name`, `cu_password`, `cu_phone`, `cu_status`, `cu_surname`, `cu_tax_administration`, `cu_tax_number`) VALUES
(1, '', 726724535, '', 'demet@mail.com', '14134134', 'Demet ', '12345', '', 2, 'Koçhan', '', 3242352),
(2, '', 726752883, 'Bilgisayar A.Ş', '', '02341343', 'Ali', '', '', 1, 'Bilmem', '', 657678679),
(3, '', 726799014, '', 'veli@mail.com', '24235245', 'Veli', '', '', 2, 'Bilir', '', 896785);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `payin`
--

CREATE TABLE `payin` (
  `pay_id` int(11) NOT NULL,
  `pay_customer_id` int(11) NOT NULL,
  `pay_detail` varchar(255) DEFAULT NULL,
  `pay_price` int(11) NOT NULL,
  `date` date DEFAULT current_timestamp(),
  `pay_receipt_no` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tablo döküm verisi `payin`
--

INSERT INTO `payin` (`pay_id`, `pay_customer_id`, `pay_detail`, `pay_price`, `date`, `pay_receipt_no`) VALUES
(1, 3, 'Ödendi', 35, '2021-09-03', 1),
(9, 2, 'okge', 20045, '2021-09-03', 4),
(12, 1, 'test', 100, '2021-09-03', 5),
(16, 1, 'test2', 5, '2021-09-03', 11);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `payout`
--

CREATE TABLE `payout` (
  `payout_id` int(11) NOT NULL,
  `payout_detail` varchar(255) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `payout_price` int(11) NOT NULL,
  `payout_title` varchar(255) DEFAULT NULL,
  `payout_type` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tablo döküm verisi `payout`
--

INSERT INTO `payout` (`payout_id`, `payout_detail`, `date`, `payout_price`, `payout_title`, `payout_type`) VALUES
(2, 'Ödendi', '2021-09-03', 100, 'Su alındı', 2),
(5, '', '2021-09-01', 20000, 'Bilgisayar Alındı', 2),
(6, 'Ödendi', '2021-09-01', 400, 'Kitap Alındı', 4),
(13, 'Ödendii', '2021-09-03', 100, 'Dondurma alındı', 2),
(17, 'Ödendi', '2021-09-04', 200, 'Tel Alındı', 2);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `product`
--

CREATE TABLE `product` (
  `pro_id` int(11) NOT NULL,
  `pro_KDV` int(11) NOT NULL,
  `pro_amount` int(11) NOT NULL,
  `pro_code` bigint(20) NOT NULL,
  `pro_detail` varchar(255) DEFAULT NULL,
  `pro_purchase_price` int(11) NOT NULL,
  `pro_sale_price` int(11) NOT NULL,
  `pro_title` varchar(255) DEFAULT NULL,
  `pro_unit` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tablo döküm verisi `product`
--

INSERT INTO `product` (`pro_id`, `pro_KDV`, `pro_amount`, `pro_code`, `pro_detail`, `pro_purchase_price`, `pro_sale_price`, `pro_title`, `pro_unit`) VALUES
(1, 1, 4, 732497124, 'Erikli Su', 2, 5, 'Su', 1),
(2, 2, 10, 732558364, 'Eti', 10, 15, 'Bisküvi', 1),
(3, 1, 10, 732675606, 'Bakır tel', 6, 10, 'Tel', 3),
(4, 2, 5, 732937000, 'Asus', 10000, 20000, 'Bilgisayar', 2),
(11, 3, 5, 828698117, 'Yazar Dan Brown', 15, 25, 'Kitap', 1);

--
-- Dökümü yapılmış tablolar için indeksler
--

--
-- Tablo için indeksler `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `boxaction`
--
ALTER TABLE `boxaction`
  ADD PRIMARY KEY (`box_id`),
  ADD KEY `FK1hv1r61mcx34aspqiam5hgjrt` (`product_pro_id`);

--
-- Tablo için indeksler `boxcustomerproduct`
--
ALTER TABLE `boxcustomerproduct`
  ADD PRIMARY KEY (`box_id`);

--
-- Tablo için indeksler `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`cu_id`);

--
-- Tablo için indeksler `payin`
--
ALTER TABLE `payin`
  ADD PRIMARY KEY (`pay_id`);

--
-- Tablo için indeksler `payout`
--
ALTER TABLE `payout`
  ADD PRIMARY KEY (`payout_id`);

--
-- Tablo için indeksler `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`pro_id`);

--
-- Dökümü yapılmış tablolar için AUTO_INCREMENT değeri
--

--
-- Tablo için AUTO_INCREMENT değeri `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(32) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Tablo için AUTO_INCREMENT değeri `boxaction`
--
ALTER TABLE `boxaction`
  MODIFY `box_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- Tablo için AUTO_INCREMENT değeri `customer`
--
ALTER TABLE `customer`
  MODIFY `cu_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Tablo için AUTO_INCREMENT değeri `payin`
--
ALTER TABLE `payin`
  MODIFY `pay_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- Tablo için AUTO_INCREMENT değeri `payout`
--
ALTER TABLE `payout`
  MODIFY `payout_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- Tablo için AUTO_INCREMENT değeri `product`
--
ALTER TABLE `product`
  MODIFY `pro_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- Dökümü yapılmış tablolar için kısıtlamalar
--

--
-- Tablo kısıtlamaları `boxaction`
--
ALTER TABLE `boxaction`
  ADD CONSTRAINT `FK1hv1r61mcx34aspqiam5hgjrt` FOREIGN KEY (`product_pro_id`) REFERENCES `product` (`pro_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
