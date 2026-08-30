<script setup>
import {
  computed,
  nextTick,
  onBeforeUnmount,
  onMounted,
  ref,
  watch,
} from "vue";
import { gsap } from "gsap";
import SiteHeader from "./components/SiteHeader.vue";
import SiteFooter from "./components/SiteFooter.vue";
import HomeView from "./views/HomeView.vue";
import ClubView from "./views/ClubView.vue";
import DepartmentsView from "./views/DepartmentsView.vue";
import EventsView from "./views/EventsView.vue";
import JoinView from "./views/JoinView.vue";

const routeViews = {
  home: HomeView,
  club: ClubView,
  works: DepartmentsView,
  events: EventsView,
  join: JoinView,
};

const route = ref(readRoute());
const theme = ref(readTheme());
const currentView = computed(() => routeViews[route.value] || HomeView);
const routeMeta = computed(
  () =>
    ({
      home: { index: "01", code: "INDEX", title: "BASE CAMP" },
      club: { index: "02", code: "ABOUT", title: "PROFILE" },
      works: { index: "03", code: "UNITS", title: "DEPARTMENTS" },
      events: { index: "04", code: "EVENTS", title: "SCHEDULE" },
      join: { index: "05", code: "JOIN", title: "OPEN CALL" },
    })[route.value] || { index: "01", code: "INDEX", title: "BASE CAMP" },
);

let pageTimeline;

function readRoute() {
  const value = window.location.hash.slice(1);
  return routeViews[value] ? value : "home";
}

function readTheme() {
  try {
    return localStorage.getItem("weiguang-theme") || "day";
  } catch {
    return "day";
  }
}

function navigate(nextRoute) {
  const next = routeViews[nextRoute] ? nextRoute : "home";
  if (window.location.hash !== `#${next}`)
    window.history.pushState({}, "", `#${next}`);
  route.value = next;
}

function syncRoute() {
  const next = readRoute();
  route.value = next;
}

function toggleTheme() {
  theme.value = theme.value === "night" ? "day" : "night";
  try {
    localStorage.setItem("weiguang-theme", theme.value);
  } catch {}
}

async function animatePage() {
  await nextTick();
  pageTimeline?.kill();
  const root = document.querySelector(".view");
  if (!root) return;

  const page = route.value;

  const focusSelector = {
    home: ".club-logo",
    club: ".about-lead",
    works: ".department-card",
    events: ".event-card",
    join: ".join-visual",
  }[page];

  pageTimeline = gsap.timeline({ defaults: { ease: "power2.out" } });

  const focus = root.querySelectorAll(focusSelector);
  if (focus.length) {
    pageTimeline.fromTo(
      focus,
      { y: page === "home" ? 7 : 4 },
      {
        y: 0,
        duration: 0.18,
        stagger: focus.length > 1 ? 0.02 : 0,
        clearProps: "transform",
      },
      "-=0.06",
    );
  }
}

watch(theme, (value) => {
  document.body.dataset.theme = value;
});

onMounted(() => {
  window.addEventListener("hashchange", syncRoute);
  window.addEventListener("popstate", syncRoute);
  document.body.dataset.theme = theme.value;
  animatePage();
});

onBeforeUnmount(() => {
  window.removeEventListener("hashchange", syncRoute);
  window.removeEventListener("popstate", syncRoute);
  pageTimeline?.kill();
});
</script>

<template>
  <div class="app-shell">
    <SiteHeader
      :active-route="route"
      :is-night="theme === 'night'"
      @navigate="navigate"
      @toggle-theme="toggleTheme"
    />
    <div class="screen-shell">
      <div class="screen-topline">
        <span>{{ routeMeta.index }} / LF-26</span>
        <span class="screen-topline-center"
          >MICRO LIGHT ANIME COMMUNITY // {{ routeMeta.title }}</span
        >
        <span class="screen-topline-status"
          ><i></i>{{ routeMeta.code }} / ONLINE</span
        >
      </div>
      <Transition name="page" mode="out-in" @after-enter="animatePage">
        <component :is="currentView" :key="route" @navigate="navigate" />
      </Transition>
    </div>
    <SiteFooter />
  </div>
</template>

<style>
.app-shell {
  min-height: 100vh;
  background: var(--paper);
}

.screen-shell {
  min-height: 100vh;
  padding-top: var(--header-height);
  position: relative;
  overflow: hidden;
}

.screen-shell::before,
.screen-shell::after {
  content: "";
  position: fixed;
  z-index: 2;
  pointer-events: none;
}

.screen-shell::before {
  width: 22px;
  height: 22px;
  top: calc(var(--header-height) + 18px);
  right: 26px;
  border-top: 1px solid var(--coral);
  border-right: 1px solid var(--coral);
}

.screen-shell::after {
  width: 32px;
  height: 1px;
  right: 26px;
  bottom: 26px;
  background: var(--cyan);
  box-shadow:
    -9px 0 0 rgba(112, 214, 208, 0.35),
    -18px 0 0 rgba(112, 214, 208, 0.15);
}

.screen-topline {
  height: 38px;
  padding: 0 clamp(22px, 4vw, 70px);
  display: flex;
  align-items: center;
  justify-content: space-between;
  position: relative;
  color: var(--muted);
  border-bottom: 1px solid var(--line);
  font: 9px var(--mono);
  letter-spacing: 0.12em;
}

.screen-topline::before {
  content: "";
  position: absolute;
  left: 0;
  bottom: -1px;
  width: 18%;
  height: 2px;
  background: var(--coral);
  animation: signal-pulse 2.8s ease-in-out infinite;
}

.screen-topline::after {
  content: "";
  width: 56px;
  height: 5px;
  margin-left: 14px;
  border-top: 1px solid var(--coral);
  border-bottom: 1px solid var(--coral);
  opacity: 0.7;
}

.screen-topline-center {
  color: var(--coral-dark);
}

.screen-topline-status {
  display: inline-flex;
  align-items: center;
  gap: 7px;
}

.screen-topline-status i {
  width: 5px;
  height: 5px;
  display: inline-block;
  border-radius: 50%;
  background: var(--cyan);
  box-shadow: 0 0 0 3px rgba(112, 214, 208, 0.14);
}

.page-enter-active,
.page-leave-active {
  transition:
    opacity 0.35s ease,
    transform 0.35s cubic-bezier(0.22, 1, 0.36, 1);
}

.page-enter-from {
  opacity: 0;
  transform: translateX(30px);
}

.page-leave-to {
  opacity: 0;
  transform: translateX(-18px);
}

@keyframes signal-pulse {
  0%,
  100% {
    transform: scaleX(0.55);
    transform-origin: left;
    opacity: 0.45;
  }
  50% {
    transform: scaleX(1);
    opacity: 1;
  }
}

@media (max-width: 880px) {
  :root {
    --header-height: 116px;
  }
  .screen-shell {
    padding-top: 0;
  }
  .screen-shell::before {
    top: 52px;
  }
  .screen-topline {
    padding: 0 22px;
  }
  .screen-topline-center,
  .screen-topline::before,
  .screen-topline::after {
    display: none;
  }
}

@media (max-width: 620px) {
  :root {
    --header-height: 112px;
  }
  .screen-topline {
    height: 32px;
    padding: 0 18px;
    font-size: 8px;
  }
  .screen-topline-status {
    font-size: 8px;
  }
}
</style>
