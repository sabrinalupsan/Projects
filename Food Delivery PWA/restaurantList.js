const restaurantList= document.getElementsByTagName('main')[0];
restaurantList.innerHTML += `

	<ul id="restaurant-list">
	</ul>

`;

APP.renderRestaurantList = (restaurantArray) => {
	// golim lista in care se vor afisa produsele
	const restaurantList = document.querySelector('#restaurant-list');
	restaurantList.innerHTML = '';
	// pentru fiecare element din vectorul primit ca parametru
	restaurantArray.forEach( (elem) => {
		// cream un produs
		rest = new Restaurant(elem.name,
            elem.image,elem.minPrice, elem.deliveryPrice,elem.description, 
            elem.priceRange,elem.deliveryTime,elem.menu,elem.id);
		// il afisam in pagina web
		rest.renderRestaurant();
	});
}