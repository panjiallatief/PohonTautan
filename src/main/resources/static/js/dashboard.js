var ImageProfile, ImageBg;

function readFormData() {
  // Get values from input fields
  var customUrl = document.getElementById("cusUrl").value;
  var headline = document.getElementById("headline").value;
  var bio = document.getElementById("bio").value;
  var dataImage = new FormData();
  var alrt = document.getElementById("ber");

  // dataI = $("#inp1")[0].files[0];
  // dataB = $("#inp2")[0].files[0];

  dataImage.append("image", ImageProfile);
  dataImage.append("bg", ImageBg);

  // console.log(ImageProfile)

  $.ajax({
    url: `/adm/inputstyle?bio=${bio}&headline=${headline}&curl=${customUrl}`,
    method: "post",
    data: dataImage,
    contentType: false,
    processData: false,
    success: function (data) {
      //   console.log(url);
      alrt.innerText = data.message;
      modalClose("edit-modal");
      // alrt.classList.remove('hidden')
      notif()
    },
  });
}

function notif(){
  openModal("alert-success");
      setTimeout(function () {
        modalClose("alert-success");
      }, 5000);
}

function dataEdit() {
  var cusUrl = $("#cusUrl").val();
  var headline = $("#headline").val();
  var bio = $("#bio").val();

  //    console.log(bio)
}
// TW Elements is free under AGPL, with commercial license required for specific uses. See more details: https://tw-elements.com/license/ and contact us for queries at tailwind@mdbootstrap.com
const colorPicker = document.getElementById("nativeColorPicker1");
// const changeColorBtn = document.getElementById("burronNativeColor");

// changeColorBtn.style.backgroundColor = colorPicker.value;
// colorPicker.addEventListener("input", () => {
//     changeColorBtn.style.backgroundColor = colorPicker.value;
// });

const modalClose = (modal) => {
  const modalToClose = document.querySelector("." + modal);
  modalToClose.classList.remove("fadeIn");
  modalToClose.classList.add("fadeOut");
  setTimeout(() => {
    modalToClose.style.display = "none";
  }, 500);
};

const openModal = (modal) => {
  const modalToOpen = document.querySelector("." + modal);
  modalToOpen.classList.remove("fadeOut");
  modalToOpen.classList.add("fadeIn");
  modalToOpen.style.display = "flex";
};

$("#addbtn").on("click", function () {
  $("#tempatlagi").append(
    `<div class="pp">
        <hr class="h-px my-8 bg-gray-200 border-0 dark:bg-gray-700">
                <label class="font-medium text-gray-800">Name</label>
                <input type="text" class="w-full outline-none rounded bg-gray-100 p-2 mt-2 mb-3" name="button_name[]"/>
                <label class="font-medium text-gray-800">Url</label>
                <input type="text" class="w-full outline-none rounded bg-gray-100 p-2 mt-2 mb-3" name="link[]"/>
                <label class="font-medium text-gray-800">Text Color</label>
                    <select name="text-col[]" class="w-full outline-none rounded bg-gray-100 p-2 mt-2 mb-3">
                        <option value="" disabled selected>--Select--</option>
                        <option value="text-white">White</option>
                        <option value="text-black">Black</option>
                    </select>

                    <label class="font-medium text-gray-800">Button Animation</label>
                    <select name="button-animate[]" class="w-full outline-none rounded bg-gray-100 p-2 mt-2 mb-3">
                        <option value="" disabled selected>--Select--</option>
                        <option value="animate-none">None</option>
                        <option value="animate-wiggle">Wiggle</option>
                        <option value="animate-ping">Ping</option>
                        <option value="animate-pulse">Pulse</option>
                    </select>

                <div class="mb-5">
                    <label for="color-picker" class="block mb-1 font-semibold">Select a color</label>
                    <input id="nativeColorPicker1" type="color" value="#6590D5" class="w-full rounded" name="button_style[]"/>
                </div>
                <div>
                    <button class="bg-red-600 hover:bg-red-800 w-full text-white rounded delete_">Cancel</button>
                </div>
            </div>`
  );
});

$("#tempatlagi").on("click", ".delete_", function () {
  $(this).parents(".pp").remove();
});

function deletebut(link) {
  const lk = link
    .replaceAll(".", "_")
    .substring(link.lastIndexOf("/") + 1, link.length);
  // const lynk = lk.subString(lk.lastIndexOf("/"), lk.length)
  // console.log(lk)
  $.ajax({
    url: `/adm/deletebutton?tautan=${lk}`,
    method: "PUT",
    success: function () {
      location.reload();
    },
  });
}

function tambahdata() {
  var inputName = document.getElementsByName("button_name[]");
  var inputLink = document.getElementsByName("link[]");
  var inputStyle = document.getElementsByName("button_style[]");
  var TextStyle = document.getElementsByName("text-col[]");
  var btnStyle = document.getElementsByName("button-animate[]");
  namaBtn = [];
  linkBtn = [];
  styleBtn = [];
  textcol = [];
  btnanim = [];
 
  for (var i = 0; i < inputName.length; i++) {
    var a = inputName[i].value;
    namaBtn.push(a);
    var b = inputLink[i].value;
    linkBtn.push(b);
    var c = inputStyle[i].value;
    styleBtn.push(c.replaceAll("#", ""));
    var d = TextStyle[i].options[TextStyle[i].selectedIndex].value;
    textcol.push(d);
    var e = btnStyle[i].options[btnStyle[i].selectedIndex].value;
    btnanim.push(e);
  }

  console.log(textcol, btnanim)

  $.ajax({
    url: `/adm/inputbutton?buttonname=${namaBtn}&tautan=${linkBtn}&tombol=${styleBtn}&buttonanim=${btnanim}&buttoncolortext=${textcol}`,
     method: "put",
    success: function () {
      modalClose("add-modal");
      notif()
    },
  });
}

$("#inp1").on("change", function () {
  var reader = new FileReader();
  reader.onload = function (event) {
    $image_crop
      .croppie("bind", {
        url: event.target.result,
      })
      // .then(function () {
      //   console.log("jQuery bind complete");
      // });
  };
  reader.readAsDataURL(this.files[0]);
  openModal("crop-modal");
});

$(".crop_image").on("click", function (event) {
  $image_crop
    .croppie("result", {
      type: "base64",
      format: "jpeg",
      //type: 'canvas',
      quality: 1,
      circle: true,
      size: "viewport",
      //size: {width: 150, height: 200}
    })
    .then(function (response) {
      $("#item-img-output").attr("src", response);
      ImageProfile = response;
      // $('#uploadimageModal').modal('hide');
      modalClose("crop-modal");
    });
});

$("#inp2").on("change", function () {
  var reader = new FileReader();
  reader.onload = function (event) {
    $bg_crop
      .croppie("bind", {
        url: event.target.result,
      })
      // .then(function () {
      //   console.log("jQuery bind complete");
      // });
  };
  reader.readAsDataURL(this.files[0]);
  openModal("cropi-modal");
});

$(".crop_imagei").on("click", function (event) {
  $bg_crop
    .croppie("result", {
      type: "blob",
      format: "jpeg",
      //type: 'canvas',
      quality: 1,
      // circle: true,
      size: "original",
      //size: {width: 150, height: 200}
    })
    .then(function (response) {
      $("#item-bg-output").attr("src", response);
      ImageBg = response;
      // $('#uploadimageModal').modal('hide');
      modalClose("cropi-modal");
    });
});
