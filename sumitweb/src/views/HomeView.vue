<script setup>
import TheWelcome from '../components/TheWelcome.vue'
import carousel from '../components/icons/carousel.vue'
import {RouterLink, RouterView} from 'vue-router'

</script>

<template>

    <TheWelcome />
    <carousel/>
    <router-view></router-view>
</template>
