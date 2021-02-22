function getLocation(map) {
    if (navigator.geolocation) {
      return navigator.geolocation.getCurrentPosition(function(pos){
        //   console.log(pos.coords)
        let icon={
           url: "https://icons.iconarchive.com/icons/paomedia/small-n-flat/1024/house-icon.png",
            // url:"https://cdn.iconscout.com/icon/free/png-256/motorcycle-delivery-2127501-1792684.png",
            scaledSize: new google.maps.Size(40,40),
            origin: new google.maps.Point(0,0),
            anchor: new google.maps.Point(0, 0)
  
        }
        const markHome =new google.maps.Marker({
            position:{lat:pos.coords.latitude, lng:pos.coords.longitude},
            map:map,
            icon:icon
        });

        //   return pos.coords;
      } );
    } 
     
  }

  function initMap(){
      const page=document.querySelector("#root");
      
      page.innerHTML+=`<div id="map"> </div>`;
      const options ={
          zoom:13,
          center: {lat:APP.cart.restaurant.address[0], lng:APP.cart.restaurant.address[1]
          }
      }
      let map= new google.maps.Map(document.querySelector("#map"),options);
      let icon={
          //https://icons.iconarchive.com/icons/paomedia/small-n-flat/1024/house-icon.png
          url:"https://cdn.iconscout.com/icon/free/png-256/motorcycle-delivery-2127501-1792684.png",
          scaledSize: new google.maps.Size(50,50),
          origin: new google.maps.Point(0,0),
          anchor: new google.maps.Point(0, 0)

      }
      let markRest= new google.maps.Marker({
          position:{lat:APP.cart.restaurant.address[0], lng:APP.cart.restaurant.address[1]},
          map:map,
          icon:icon
      });
      let homeCoordinates= getLocation(map);
      console.log(homeCoordinates);
      
    // getLocation();
  }

  