function load(){
    // $.ajax({
    //     url : ``,
    //     method : "GET",
    //     success : function (){
    //         $("#buttontmpt").append(
    //             `<div class="relative">
    //                 <button class="min-w-full bg-emerald-500 hover:bg-emerald-600 active:bg-emerald-800 rounded-lg px-4 py-2 mb-3">Lorem ipsum dolor </button>
    //                 <span class="absolute top-2 -right-4 h-4 w-4 mt-1 mr-2 bg-red-500 rounded-full cursor-pointer hover:animate-pulse"><span class="text-white absolute top-[-6px] left-[3.5px]">x</span></span>
    //                             </div>`
    //         )
    //     }
    // })
}

function edit() {
    // $("#buttontmpt").append(
    //     `<div class="relative">
    //         <button class="min-w-full bg-emerald-500 hover:bg-emerald-600 active:bg-emerald-800 rounded-lg px-4 py-2 mb-3">Lorem ipsum dolor </button>
    //         <span class="absolute top-2 -right-4 h-4 w-4 mt-1 mr-2 bg-red-500 rounded-full cursor-pointer hover:animate-pulse"><span class="text-white absolute top-[-6px] left-[3.5px]">x</span></span>
    //     </div>`
    // )
}
// TW Elements is free under AGPL, with commercial license required for specific uses. See more details: https://tw-elements.com/license/ and contact us for queries at tailwind@mdbootstrap.com 
const colorPicker = document.getElementById("nativeColorPicker1");
// const changeColorBtn = document.getElementById("burronNativeColor");

// changeColorBtn.style.backgroundColor = colorPicker.value;
// colorPicker.addEventListener("input", () => {
//     changeColorBtn.style.backgroundColor = colorPicker.value;
// });

function toggleModal() {
    document.getElementById('modal').classList.toggle('hidden')
}

$("#addbtn").on("click", function () {
    $("#tempatlagi").append(
        `<div class="pp">
                <label class="font-medium text-gray-800">Name</label>
                <input type="text" class="w-full outline-none rounded bg-gray-100 p-2 mt-2 mb-3" name="button_name[]"/>
                <label class="font-medium text-gray-800">Url</label>
                <input type="text" class="w-full outline-none rounded bg-gray-100 p-2 mt-2 mb-3" name="link[]"/>

                <div>
                    <label for="color-picker" class="block mb-1 font-semibold">Select a color</label>
                    <input id="nativeColorPicker1" type="color" value="#6590D5" class="w-full rounded" name="button_style[]"/>
                </div>
                <div>
                    <button class="bg-red-600 hover:bg-red-800 w-full text-white rounded delete_">Cancel</button>
                </div>
            </div>`
    )
})

$("#tempatlagi").on("click", ".delete_", function () {
    $(this).parents(".pp").remove();
});

function deletebut(link){
    const lk = link.replaceAll(".", "_").substring(link.lastIndexOf("/")+1, link.length)
    // const lynk = lk.subString(lk.lastIndexOf("/"), lk.length)
    // console.log(lk)
    $.ajax({
        url: `/deletebutton?tautan=${lk}`,
        method: 'PUT',
    })
}

function tambahdata() {
    var inputName = document.getElementsByName("button_name[]");
    var inputLink = document.getElementsByName("link[]");
    var inputStyle = document.getElementsByName("button_style[]");
    // listVideo = [];
    namaBtn = [];
    linkBtn = [];
    styleBtn = [];

    for (var i = 0; i < inputName.length; i++) {
        var a = inputName[i].value;
        namaBtn.push(a);
        var b = inputLink[i].value;
        linkBtn.push(b)
        var c = inputStyle[i].value;
        styleBtn.push(c.replaceAll("#", ""))
    }

    $.ajax({
        url: `/adm/inputbutton?buttonname=${namaBtn}&tautan=${linkBtn}&tombol=${styleBtn}`,
        method: "put",
        success: function () {
            console.log('berhasil')
            console.log(namaBtn)
            console.log(linkBtn)
            console.log(styleBtn)
            console.log(url)
        }
    })

}