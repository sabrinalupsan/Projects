class Restaurant{
    constructor(name,image,minPrice,deliveryPrice,description,priceRange,
        deliveryTime,menu,id)
        {   this.id=id;
            this.name=name;
            this.image=image;
            this.minPrice=minPrice;
            this.deliveryPrice=deliveryPrice;
            this.description=description;
            this.priceRange=priceRange;
            this.deliveryTime=deliveryTime;
            this.menu=menu;
            // APP.menus.push({id:this.id,
            // menu:this.menu});
        }

        renderMenu(){
            console.log('RENDER MENU')
            const restaurant=document.querySelector('#Menu'+this.id+"body");
            // restaurant.innerHTML+=``;
            // restaurant.innerHTML+=`<form id='addToCartForm' >
            // `;
            // APP.menus.push({
            //     restaurant:this.id,
            //     menu:{}
            // })
            // restaurant.innerHTML += `
            //         <table>
            //             <tbody>
            //     `;
            
            let i =0;
            // let menu={id: this.id,
            // products:[]};

            Object.keys(this.menu).forEach((item)=>{
                
                

                restaurant.innerHTML+=`
                <tr>
                    <td>
                        <h2>${item}</h2>
                    </td>
                </tr>
                `;

                this.menu[item].forEach((item)=>{
                    let itemName=this.id+ "prodName"+i;    
                    let itemNameName =item.name.replace(/\s/g, '');
                    itemNameName=itemNameName.replace("'","");
                    let noProducts=this.id+"noProd"+i;
                    // let itemName= item.name.replace(/\s/g, '/');


                    // let value=[this.name,item.name,item.price];
                    restaurant.innerHTML+=`
                    <tr>
                        <td id="productname"><label> ${item.name} ${item.price} lei</label></td>
                        <td><input type="number" name=${noProducts}></td>
                        <td><input hidden type="text" name=${itemName} value=${itemNameName}></td>
                    </tr>
                    `
                    // menu.products.push(itemName);
                        i++;
                })
            });
            restaurant.innerHTML+=`<tr><td><button name="order${this.name}" class = "buttons" type="submit">Add to cart</button></td></tr>`;
            // restaurant.innerHTML += `
            //         </tbody>
            //     </table>   
            //     `;
        }
        // addFormFunctionality() {
        
        //     const form = document.querySelector("#Menu"+this.id);
        //     form.onsubmit=function(event){
        //         event.preventDefault();
                
        //       console.log ("aa");
        //     }
    
        // }
        renderRestaurant(){
            const restaurantList=document.querySelector('#restaurant-list')
            let idC="Menu"+this.id;
            restaurantList.innerHTML+=`
            <li class = 'restaurant' id=${this.id}>
                <div class = "profilediv" ondrop="drop(event)" ondragover="allowDrop(event)">
                    <img class="profile" src= ${this.image} alt="Restaurant logo" id="${idC}logo" draggable="true" ondragstart="drag(event)">
                </div>
                <div id="restaurantdiv">
                    <h2>${this.name}</h2>
                    <p>${this.description}<p>
                    <img class="icon" src='https://upload.wikimedia.org/wikipedia/commons/thumb/0/08/Simple_icon_time.svg/1200px-Simple_icon_time.svg.png'>
                    <span>${this.deliveryTime}</span>
                    <br>
                    <img class="icon" src='https://i.pinimg.com/originals/5c/44/92/5c44929ceecbafc93b5bf1320aeb574f.png'>
                    <span>${this.deliveryPrice} lei</span>
                    <br>
                    <img class="icon" src='https://simpleicon.com/wp-content/uploads/basket.png'>
                    <span>Min. ${this.minPrice} lei</span>
                    <br>
                    <span>${this.priceRange}</span>
                    <br />
                    <button id="${idC}button" class = "buttons" onclick="showMenu('${idC}formdiv','${idC}button')">Show menu</button>
                </div>
                <div class="formdiv" id="${idC}formdiv">
                    <form class="theforms" id=${idC}>
                        <table>
                            <tbody id=${idC}body>
                            </tbody>
                        </table>
                    </form>
                </div>
            </li>
            `
                this.renderMenu();
            //    this.addFormFunctionality();
        }

        
        
    }

    // const form = document.querySelector('#');

    function addFormFunctionality() {
        
        const form = document.querySelector('#Menukfc');
        const form1 = document.querySelector('#Menudominos');
        const form2 = document.querySelector('#MenumcDonalds');

        form.onsubmit=function(event){
            event.preventDefault();

        //   console.log (event.target.kfcprodName0.value);
        let i=0;
        let rest=APP.restaurants[1];
        let order=[];

        Object.keys(rest.menu).forEach((item)=>{

            rest.menu[item].forEach((item)=>{
            let noProd=rest.id+ "noProd"+i;
            // console.log(noProd);
            // console.log(event.target.noProd);

            if(event.target[noProd].value>0){
                    // console.log(event.target[noProd].value);

                    order.push({ product:item.name,
                    price:item.price,
                    noProducts: parseInt(event.target[noProd].value)
             });
                console.log(order);
                }
            i++;
            })
        })
        // APP.restaurants[0].menu

        APP.cart.restaurant=rest;
        APP.cart.products=order;
        window.alert("Items were added to your cart!");

        // console.log(APP.restaurants[0].menu.popular[0].name.);

        }


        form1.onsubmit=function(event){
        
            event.preventDefault();
            let i=0;
            let rest=APP.restaurants[0];
            let order=[];
            Object.keys(rest.menu).forEach((item)=>{

                rest.menu[item].forEach((item)=>{
                let noProd=rest.id+ "noProd"+i;
                // console.log(noProd);
                // console.log(event.target.noProd);
    
                if(event.target[noProd].value>0){
                        // console.log(event.target[noProd].value);
    
                        order.push({ product:item.name,
                        price:item.price,
                    noProducts: parseInt(event.target[noProd].value)
                 });
                    console.log(order);
                    }
                i++;
                })
            })
            APP.cart.restaurant=rest;
            APP.cart.products=order;
            window.alert("Items were added to your cart!");

        //   console.log (event.target.dominosprodName0.value);
        }
        form2.onsubmit=function(event){
            event.preventDefault();

            let i=0;
            let rest=APP.restaurants[2];
            let order=[];
            Object.keys(rest.menu).forEach((item)=>{

                rest.menu[item].forEach((item)=>{
                let noProd=rest.id+ "noProd"+i;
                // console.log(noProd);
                // console.log(event.target.noProd);
    
                if(event.target[noProd].value>0){
                        // console.log(event.target[noProd].value);
    
                        order.push({ product:item.name,
                        price:item.price,
                    noProducts: parseInt(event.target[noProd].value)
                 });
                    console.log(order);
                    }
                i++;
                })
            })
            APP.cart.restaurant=rest;
            APP.cart.products=order;

            window.alert("Items were added to your cart!");

        //   console.log (event.target.mcDonaldsprodName0.value);
        }
    }
    window.addEventListener('load', addFormFunctionality);

function showMenu(id, idbutton)
{
    var formdiv = document.getElementById(id);
    var button = document.getElementById(idbutton)
    if (formdiv.style.display == "block")
    {
        formdiv.style.display = "none";
        button.innerHTML='Show menu'
    }
    else
    {
        formdiv.style.display = "block";
        button.innerHTML='Hide menu'
    }
}

function drag(ev) {
    ev.dataTransfer.setData("text", ev.target.id);
}

function allowDrop(ev) {
    ev.preventDefault();
}

function drop(ev) {
    ev.preventDefault();
    var data = ev.dataTransfer.getData("text");
    ev.target.appendChild(document.getElementById(data));
}