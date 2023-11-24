<template>
    
    <div class="isolate bg-white px-6 py-3 sm:py-10 lg:px-8">
        <RouterLink :to="'/logi'" class="text-base font-semibold leading-6 text-gray-900"><span aria-hidden="true">&larr;</span>Log in</RouterLink>
      <div class="absolute inset-x-0 top-[-10rem] -z-10 transform-gpu overflow-hidden blur-3xl sm:top-[-20rem]" aria-hidden="true">
        <div class="relative left-1/2 -z-10 aspect-[1155/678] w-[36.125rem] max-w-none -translate-x-1/2 rotate-[30deg] bg-gradient-to-tr from-[#ff80b5] to-[#9089fc] opacity-30 sm:left-[calc(50%-40rem)] sm:w-[72.1875rem]" style="clip-path: polygon(74.1% 44.1%, 100% 61.6%, 97.5% 26.9%, 85.5% 0.1%, 80.7% 2%, 72.5% 32.5%, 60.2% 62.4%, 52.4% 68.1%, 47.5% 58.3%, 45.2% 34.5%, 27.5% 76.7%, 0.1% 64.9%, 17.9% 100%, 27.6% 76.8%, 76.1% 97.7%, 74.1% 44.1%)" />
      </div>
      <div class="mx-auto max-w-2xl text-center">
        <h2 class="text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl">註冊帳戶</h2>
        <p class="mt-2 text-lg leading-8 text-gray-600">Aute magna irure deserunt veniam aliqua magna enim voluptate.</p>
      </div>
      <form action="#" method="POST" class="mx-auto mt-16 max-w-xl sm:mt-20">
        <div class="grid grid-cols-1 gap-x-8 gap-y-6 sm:grid-cols-2">
          <div>
            <label for="first-name" class="block text-sm font-semibold leading-6 text-gray-900">First name</label>
            <div class="mt-2.5">
              <input type="text" name="first-name" id="first-name" autocomplete="given-name" class="block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
            </div>
          </div>
          <div>
            <label for="last-name" class="block text-sm font-semibold leading-6 text-gray-900">Last name</label>
            <div class="mt-2.5">
              <input type="text" name="last-name" id="last-name" autocomplete="family-name" class="block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
            </div>
          </div>

          <div class="sm:col-span-2">
            <label for="email" class="block text-sm font-semibold leading-6 text-gray-900">Email</label>
            <div class="mt-2.5">
                <input type="email" name="email" id="userEmail" autocomplete="email" v-model="userEmail" class="block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
                <div v-if="emailError" class="text-red-500 text-sm mt-2">請輸入有效的電子郵件地址。</div>
            </div>
          </div>
          <div class="sm:col-span-2">
              <label for="password" class="block text-sm font-semibold leading-6 text-gray-900">Password</label>
            <div class="mt-2.5">
                <input type="password" name="password" v-model="password" id="password"  class="block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
            </div>
        </div>
        <div class="sm:col-span-2">
            <label for="checkpassword" class="block text-sm font-semibold leading-6 text-gray-900">Check password</label>
            <div class="mt-2.5">
              <input type="password" name="checkpassword" id="checkpassword" class="block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
            </div>
          </div>
        </div>
        <div class="mt-10">
            <button type="button" @click="storeEmail" class="block w-full rounded-md bg-indigo-600 px-3.5 py-2.5 text-center text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">註冊</button>
        </div>
      </form>
    </div>
  </template>
  <script>
    import { ref } from 'vue';
    import { RouterLink } from 'vue-router';
    
    export default {
      components: {},
      data() {
        return {
          userEmail: '',
          password: '',
          emailError: false,
          checkPassword: ''
        };
      },
      methods: {
        // 註冊用戶
        registerUser(email, password) {
          // 檢查是否用戶已存在
          if (localStorage.getItem(email)) {
            return "用戶已存在";
          }
    
          // 在 localStorage 中存儲用戶信息
          localStorage.setItem(email, password);
          return "註冊成功";
        },
    
        storeEmail() {
          if (this.isValidEmail(this.userEmail)) {
            localStorage.setItem('email', this.userEmail); // 存儲用戶輸入的電子郵件地址
            localStorage.setItem('password', this.password); // 存儲用戶輸入的密碼
            alert('電子郵件地址和密碼已存儲在 localStorage 中。');
          } else {
            alert('請輸入有效的電子郵件地址。');
          }
        },
    
        isValidEmail(email) {
          // 使用正則表達式來驗證電子郵件地址的格式
          const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
          return emailPattern.test(email);
        },
        validateEmail() {
      // 使用正則表達式來驗證電子郵件地址的格式
      const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
      this.emailError = !emailPattern.test(this.userEmail);
    },

    // storeEmail() {
    //   if (!this.emailError) {
    //     localStorage.setItem('email', this.userEmail); // 存儲用戶輸入的電子郵件地址
    //     localStorage.setItem('password', this.password); // 存儲用戶輸入的密碼
    //     alert('電子郵件地址和密碼已存儲在 localStorage 中。');
    //   } else {
    //     alert('請輸入有效的電子郵件地址。');
    //   }
    // },
      },
    };
    </script>