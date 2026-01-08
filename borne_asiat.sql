-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : jeu. 08 jan. 2026 à 19:26
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `borne_asiat`
--

-- --------------------------------------------------------

--
-- Structure de la table `menu_items`
--

CREATE TABLE `menu_items` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` text NOT NULL,
  `price` decimal(10,0) NOT NULL,
  `category` varchar(20) NOT NULL,
  `image_url` varchar(255) NOT NULL,
  `is_available` tinyint(1) NOT NULL DEFAULT 0,
  `is_spicy` tinyint(1) NOT NULL DEFAULT 0,
  `is_vegetarian` tinyint(1) NOT NULL DEFAULT 0,
  `calories` int(11) NOT NULL,
  `protein_required` tinyint(1) NOT NULL DEFAULT 0,
  `descriptionEng` varchar(1000) NOT NULL,
  `nameEng` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `menu_items`
--

INSERT INTO `menu_items` (`id`, `name`, `description`, `price`, `category`, `image_url`, `is_available`, `is_spicy`, `is_vegetarian`, `calories`, `protein_required`, `descriptionEng`, `nameEng`) VALUES
(1, 'Ramen au porc Tonkotsu', 'Bouillon riche, nouilles de blé, porc braisé, œuf mariné, algues.', 10, 'Plats principaux', 'com/images/ramen_tonkotsu.png', 1, 0, 0, 820, 0, 'Rich broth, wheat noodles, braised pork, marinated egg, seaweed.', 'Ramen with pork Tonkotsu'),
(2, 'Pad Thai', 'Nouilles de riz sautées, cacahuètes, pousses de soja, sauce tamarin.', 10, 'Plats principaux', 'com/images/pad_thai.png', 0, 0, 0, 780, 1, 'Fried rice noodles, peanuts, soy sprouts, tamarind sauce.', 'Pad Thai'),
(3, 'Bibimbap', 'Riz chaud, légumes variés, œuf au plat, sauce gochujang.', 11, 'Plats principaux', 'com/images/bibimbap.png', 1, 1, 0, 850, 1, 'Hot rice, varied vegetables, fried egg, gochujang sauce.', 'Bibimbap'),
(4, 'Pho', 'Bouillon parfumé, nouilles de riz, coriandre, basilic.', 10, 'Plats principaux', 'com/images/pho.png', 1, 0, 0, 520, 1, 'Flavorful broth, rice noodles, cilantro, basil.', 'Pho'),
(5, 'Poulet Général Tao', 'Poulet frit croustillant, sauce sucrée-salée, riz blanc.', 9, 'Plats principaux', 'com/images/general_tao.png', 1, 0, 0, 980, 0, 'Fried chicken, sweet-salty sauce, white rice.', 'General Tao chicken'),
(6, 'Curry japonais', 'Riz blanc nappé d’un curry épicé aux légumes et poulet.', 8, 'Plats principaux', 'com/images/curry_japonais.png', 1, 1, 0, 760, 0, 'White rice with a spicy curry sauce and vegetables and chicken.', 'Japanese curry'),
(7, 'Nouilles sautées', 'Nouilles de blé, légumes croquants, sauce soja.', 9, 'Plats principaux', 'com/images/nouilles_sautees.png', 1, 0, 0, 820, 1, 'Wheat noodles, crunchy vegetables, soy sauce.', 'Fried wheat noodles'),
(8, 'Canard laqué express', 'Riz parfumé, tranches de canard rôti, sauce hoisin.', 12, 'Plats principaux', 'com/images/canard_laque.png', 1, 0, 0, 980, 0, 'Flavored rice, slices of roasted duck, hoisin sauce.', 'Express Peking duck'),
(9, 'Gyoza au porc', 'Raviolis japonais grillés, farce porc et chou, sauce soja-sésame.', 8, 'Plats principaux', 'com/images/gyoza_porc.png', 1, 0, 0, 520, 0, 'Grilled Japanese ravioli, pork and cabbage filling, soy-sesame sauce.', 'Pork gyoza'),
(10, 'Banh Mi', 'Baguette croustillante, poulet mariné, pickles, coriandre.', 7, 'Plats principaux', 'com/images/banh_mi.png', 1, 0, 0, 650, 0, 'Crispy baguette, marinated chicken, pickles, cilantro.', 'Banh Mi'),
(11, 'Yakitori', 'Poulet grillé, sauce soja sucrée.', 6, 'Snacks', 'com/images/yakitori.png', 1, 0, 0, 360, 0, 'Grilled chicken, sweet soy sauce.', 'Yakitori'),
(12, 'Tempura de crevettes', 'Crevettes croustillantes, sauce tentsuyu.', 7, 'Snacks', 'com/images/tempura_crevettes.png', 1, 0, 0, 520, 0, 'Crispy shrimp, sauce tentsuyu.', 'shrimp tempura'),
(13, 'Rouleaux de printemps', 'Rouleaux de riz, crevettes, vermicelles, herbes fraîches.', 6, 'Snacks', 'com/images/spring_rolls.png', 1, 0, 0, 420, 0, 'Rice rolls, shrimp, vermicelli, fresh herbs.', 'Spring rolls'),
(14, 'Edamame', 'Fèves de soja vapeur, légèrement salées.', 4, 'Snacks', 'com/images/edamame.png', 1, 0, 1, 210, 0, 'Steamed soybeans, lightly salted.', 'Edamame'),
(15, 'Nems au porc', 'Rouleaux frits croustillants, farce porc et légumes.', 5, 'Snacks', 'com/images/nems_porc.png', 1, 0, 0, 480, 0, 'Rice rolls, pork and vegetables', 'Spring rolls with pork'),
(16, 'Bao buns', 'Pains vapeur, légumes croquants, sauce maison.', 6, 'Snacks', 'com/images/bao_buns.png', 1, 0, 0, 520, 1, 'Steam buns, crunchy vegetables, homemade sauce.', 'Bao buns'),
(17, 'Salade de papaye verte', 'Papaye râpée, cacahuètes, sauce nuoc-mam, piment doux.', 6, 'Snacks', 'com/images/salade_papaye.png', 1, 0, 1, 280, 0, 'Green papaya, peanuts, nuoc-mam sauce, mild chili.', 'green papaya salad'),
(18, 'Soupe miso', 'Bouillon miso, tofu, algues wakame.', 4, 'Snacks', 'com/images/soupe_miso.png', 1, 0, 1, 120, 0, 'Miso broth, tofu, wakame seaweed.', 'Miso soup'),
(19, 'Satay', 'Poulet grillé, sauce cacahuète thaï.', 6, 'Snacks', 'com/images/satay.png', 1, 0, 0, 540, 0, 'Grilled chicken, Thai peanut sauce.', 'Satay'),
(20, 'Okonomiyaki mini', 'Pancake japonais au chou, porc, sauce okonomi.', 7, 'Snacks', 'com/images/okonomiyaki.png', 1, 0, 0, 650, 0, 'Japanese pancake with cabbage, pork, okonomi sauce.', 'little Okonomiyaki'),
(21, 'Mochis glacés', 'Boules de pâte de riz fourrées de glace (mangue, thé vert, coco).', 5, 'Desserts', 'com/images/mochis.png', 1, 0, 1, 320, 0, 'Rice dough balls filled with ice cream (mango, green tea, coconut).', 'mochi ice cream'),
(22, 'Perles de coco vapeur', 'Boulettes de riz gluant fourrées à la pâte de haricot et de coco.', 4, 'Desserts', 'com/images/perles_coco.png', 1, 0, 1, 280, 0, 'Rice dough balls filled with coconut paste.', 'Steamed coconut pearls'),
(23, 'Dorayaki chocolat', 'Pancakes japonais fourrés au chocolat.', 5, 'Desserts', 'com/images/dorayaki_chocolat.png', 1, 0, 1, 360, 0, 'Japanese pancakes filled with chocolate.', 'Chocolate dorayaki'),
(24, 'Cheesecake matcha', 'Gâteau crémeux parfumé au matcha.', 6, 'Desserts', 'com/images/cheesecake_matcha.png', 1, 0, 1, 420, 0, 'Creamy cake with matcha flavor.', 'Cheesecake matcha'),
(25, 'Banane frit tempura', 'Banane croustillante, nappée de miel.', 4, 'Desserts', 'com/images/banane_tempura.png', 1, 0, 1, 380, 0, 'Crispy banana, drizzled with honey.', 'Banana fried tempura'),
(26, 'Bubble tea', 'Thé au lait avec perles de tapioca.', 5, 'Boissons', 'com/images/bubble_tea.png', 1, 0, 1, 420, 0, 'Tea with milk and tapioca pearls.', 'Bubble tea'),
(27, 'Tartelette sésame noir', 'Pâte sablée garnie de crème au sésame noir.', 5, 'Desserts', 'com/images/tartelette_sesame.png', 1, 0, 1, 390, 0, 'Shortbread pastry filled with black sesame cream.', 'Black sesame tartlet'),
(28, 'Glace litchi', 'Sorbet frais au litchi.', 5, 'Desserts', 'com/images/glace_litchi.png', 1, 0, 1, 210, 0, 'Fresh lychee sorbet', 'Litchi ice cream'),
(29, 'Matcha latte', 'Lait chaud parfumé au thé vert matcha.', 4, 'Boissons', 'com/images/matcha_latte.png', 1, 0, 1, 240, 0, 'Hot tea with matcha flavor.', 'Matcha latte'),
(30, 'Tapioca lait de coco', 'Dessert thaï : tapioca, lait de coco, mangue.', 5, 'Desserts', 'com/images/tapioca_coco.png', 1, 0, 1, 430, 0, 'Thai dessert: tapioca, coconut milk, mango.', 'Coconut milk tapioca');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `menu_items`
--
ALTER TABLE `menu_items`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `menu_items`
--
ALTER TABLE `menu_items`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
