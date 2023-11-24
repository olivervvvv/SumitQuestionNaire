<template>
  <div class="content">
    <div class="left">
        <RouterLink :to="'/'" class="text-base font-semibold leading-6 text-gray-900"><span aria-hidden="true">&larr;</span>home</RouterLink>
              <div class="block text-sm font-medium leading-6 text-gray-900">
                <img class="mx-auto h-16 w-auto" src="../../public/snowman-1.png" alt="Your Company" />
                <h2 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Log in to your account</h2>
              </div>
          
              <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
                <form class="space-y-6" action="#" method="POST">
                  <div>
                    <label for="email" class="block text-sm font-medium leading-6 text-gray-900">Email address</label>
                    <div class="mt-2">
                      <input id="email" name="email" type="email" v-model="userEmail" autocomplete="email" class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
                    </div>
                  </div>
          
                  <div>
                    <div class="flex items-center justify-between">
                      <label for="password" class="block text-sm font-medium leading-6 text-gray-900">Password</label>
                    </div>
                    <div class="mt-2">
                      <input id="password" name="password" type="password" v-model="userPassword" autocomplete="current-password" required="" class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
                      <div class="text-sm passwareta">
                        <a href="#" class="font-semibold text-indigo-600 hover:text-indigo-500 ">Forgot password?</a>
                      </div>
                    </div>
              <button type="button" @click="CheckEmail" class="flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white color-#a3a3a3 shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">Log in</button>
            </div>
          </form>
    
          <p class="mt-10 text-center text-sm text-gray-500">
            Not a member?
            {{ ' ' }}
            <RouterLink :to="'sign'" class="font-semibold leading-6 text-indigo-600 hover:text-indigo-500">Start a 14 day free trial</RouterLink>
            <!-- <a href="#" class="font-semibold leading-6 text-indigo-600 hover:text-indigo-500">Start a 14 day free trial</a> -->
          </p>
        </div>
      </div>
      <div class="right">
        <select name="Language" id="Language" class="Language" @change="changeLanguage">
          <option value="CN">繁中</option>
          <option value="EN">English</option>
          <option value="JP">日本語</option>
        </select>
        <!-- <button type="button">註冊</button> -->
        <RouterLink :to="'sign'" class="button">Sign in</RouterLink>

      </div>
  </div>
  </template>
<script>
  import { RouterLink, RouterView } from 'vue-router';

  import { ref } from 'vue';

  export default {
    components: {},
    data() {
      return {
        // userEmail: '',
        // password: '',
        emailError: false,
        checkPassword: ''
      };
    },
    methods: {
// 從 localStorage 獲取帳號和密碼
    CheckEmail() {
    const email = localStorage.getItem('email');
    const password = localStorage.getItem('password');
  
  // 檢查是否存在
    if (email==this.userEmail && password==this.userPassword) {
    // 做一些處理，例如登錄

    // 如果登錄成功，導航到指定頁面
    this.$router.push({ name: 'math' }); // 使用路由名稱或路徑
      } 
    else {
    // 沒有存儲的帳號和密碼，需要用戶進行註冊或登錄
      alert('用戶名或密碼錯誤');
      return "用戶名或密碼錯誤";
      }
    },
    changeLanguage(event) {
    const selectedValue = event.target.value;
    // 根據選擇的值執行路由導航
    if (selectedValue === 'CN') {
      this.$router.push({ name: 'logi' });
    } 
    if (selectedValue === 'EN') {
      this.$router.push({ name: 'logiEN' });
    } 
    if (selectedValue === 'JP') {
      // 執行其他日語路由導航操作
    }
  }

}

}

</script>

<style lang="scss" scoped>
.RL-margin{
margin-top: 3%;
}
.content{
display: flex;
flex-direction: row;
width: 100vw;
}
.left{
width: 50vw;
margin-left: 3%;
margin-top: 3%;
}
.right{
width: 50vw;
height: 100vh;
display: flex;
justify-content: flex-end;
align-items: flex-start;
background-image: url(/public/questionnaire.jpg);
background-position: center;
background-size: 100% 100%;

.button{
  margin: 3%;
  margin-right: 5%;
  width: 70px;
  height: 40px;
  color: white;
  border: 1px solid white;
  border-radius: 5px;
  display: flex;
  align-items: center;
  justify-content: center;
  text-decoration: none;
  &:hover{
    background-color: white;
    color: black;
  }
}
select{
  margin: 3%;
  width: 90px;
  height: 40px;
  color: black;
  border: 1px solid white;
  border-radius: 5px;
  text-align: center;
  option{
    height: 1000px;
  }
}
}
.passwareta{
display: flex;
justify-content: end;
margin-top: 3%;
margin-bottom: 5%;
}

</style>
