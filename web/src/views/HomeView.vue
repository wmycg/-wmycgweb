<script setup>
import logo from "../../image/1757438527327.png";
import { club } from "../data/club.js";
import { events } from "../data/events.js";
import Eyebrow from "../components/Eyebrow.vue";
import SectionHeading from "../components/SectionHeading.vue";
import EventCard from "../components/EventCard.vue";

defineEmits(["navigate"]);
</script>

<template>
  <main class="view view-home">
    <section class="hero page-section">
      <div class="hero-copy">
        <Eyebrow label="ANIME · IMAGE · COMMUNITY" />
        <h1 class="hero-title">让喜欢的事，<br />发出微光。</h1>
        <p class="hero-intro">{{ club.intro }}</p>
        <div class="hero-actions">
          <button
            class="button button-primary"
            type="button"
            @click="$emit('navigate', 'works')"
          >
            浏览部门档案 →</button
          ><button
            class="button button-quiet"
            type="button"
            @click="$emit('navigate', 'club')"
          >
            认识微光漫摄
          </button>
        </div>
        <div class="stat-strip">
          <div v-for="stat in club.stats" :key="stat.label" class="stat">
            <strong>{{ stat.value }}</strong
            ><span>{{ stat.label }}</span>
          </div>
        </div>
      </div>
      <div class="hero-art">
        <div class="art-rings"></div>
        <div class="hero-label hero-label-top">
          <span>EST.</span><strong>2026</strong>
        </div>
        <img class="club-logo" :src="logo" alt="微光漫摄社团徽章" />
        <div class="hero-stamp"><span>FRAME</span><span>01 / 05</span></div>
        <p class="badge-caption">SCHOOL OF SOFTWARE · NANCHANG UNIVERSITY</p>
      </div>
    </section>
    <section class="manifesto page-section">
      <div class="manifesto-index"><span>01</span><span>MANIFESTO</span></div>
      <div class="manifesto-body">
        <h2><br />我们把热爱做成作品。</h2>
        <p>
          一群喜欢动画、漫画、游戏与影像的年轻人，在课表的缝隙里共同创作。这里没有标准答案，只有每个人独特的观看方式。
        </p>
        <button
          class="text-link"
          type="button"
          @click="$emit('navigate', 'club')"
        >
          关于我们 ↗
        </button>
      </div>
      <div class="manifesto-side">
        <span>“</span>
        <p>每一束微光，<br />都值得被看见。</p>
      </div>
    </section>
    <section class="upcoming page-section">
      <SectionHeading
        kicker="NEXT ON THE REEL"
        title="接下来，一起做什么？"
        copy="把日期圈起来，现场见。"
      />
      <div class="event-preview">
        <EventCard
          v-for="(event, index) in events.slice(0, 3)"
          :key="event.id"
          :event="event"
          :index="index"
        />
      </div>
      <button
        class="button button-outline"
        type="button"
        @click="$emit('navigate', 'events')"
      >
        查看完整日历 →
      </button>
    </section>
  </main>
</template>

<style scoped>
.page-section {
  padding: clamp(64px, 8vw, 110px) clamp(24px, 9vw, 150px);
}
.hero {
  min-height: calc(100dvh - var(--header-height) - 38px);
  padding-top: clamp(56px, 8vw, 100px);
  display: grid;
  grid-template-columns: minmax(0, 1.1fr) minmax(380px, 0.9fr);
  gap: 4vw;
  position: relative;
  overflow: hidden;
  border-bottom: 1px solid var(--line);
}
.hero::before {
  content: "01 / SIGNAL RECEIVED";
  position: absolute;
  top: 26px;
  left: clamp(24px, 9vw, 150px);
  color: var(--coral-dark);
  font: 9px var(--mono);
  letter-spacing: 0.14em;
}
.hero-copy {
  max-width: 620px;
  align-self: center;
  position: relative;
  z-index: 1;
}
.hero-copy .eyebrow::after {
  content: "";
  width: 46px;
  height: 1px;
  margin-left: 8px;
  background: var(--cyan);
}
.hero-title {
  max-width: 680px;
  margin: 0;
  white-space: pre-line;
  font: 600 clamp(52px, 7vw, 104px)/0.98 var(--serif);
  letter-spacing: -0.03em;
  text-shadow: 5px 5px 0 rgba(196, 82, 78, 0.12);
}
.hero-intro {
  max-width: 455px;
  margin: 34px 0 30px;
  color: var(--muted);
  font-size: 14px;
  line-height: 1.9;
}
.hero-actions {
  margin-bottom: 64px;
  display: flex;
  align-items: center;
  gap: 20px;
}
.button,
.text-link {
  border: 0;
  background: transparent;
  color: var(--ink);
  font-size: 12px;
  letter-spacing: 0.08em;
}
.button {
  min-height: 44px;
  padding: 0 21px;
  border: 1px solid var(--ink);
  transition:
    background 0.2s,
    border-color 0.2s,
    color 0.2s,
    transform 0.2s;
}
.button-primary {
  background: var(--coral);
  border-color: var(--coral);
  color: #fffaf2;
}
.button-primary:hover {
  background: var(--coral-dark);
  border-color: var(--coral-dark);
  transform: translateY(-2px);
}
.button-quiet {
  padding-left: 0;
  border-color: transparent;
  color: var(--muted);
}
.stat-strip {
  display: flex;
  gap: 44px;
}
.stat {
  min-width: 86px;
  padding-left: 13px;
  display: flex;
  flex-direction: column;
  gap: 4px;
  border-left: 2px solid var(--coral);
}
.stat strong {
  font: 500 32px var(--serif);
}
.stat span {
  color: var(--muted);
  font: 9px var(--mono);
  letter-spacing: 0.12em;
  text-transform: uppercase;
}
.hero-art {
  min-height: 500px;
  position: relative;
  display: grid;
  place-items: center;
  background: var(--soft-band);
  border: 1px solid var(--line);
  clip-path: polygon(8% 0, 100% 0, 100% 92%, 92% 100%, 0 100%, 0 8%);
}
.hero-art::before,
.hero-art::after {
  position: absolute;
  z-index: 2;
  color: var(--coral-dark);
  font: 9px var(--mono);
  letter-spacing: 0.12em;
}
.hero-art::before {
  content: "VISUAL UNIT / 001";
  top: 18px;
  left: 22px;
}
.hero-art::after {
  content: "REC / LIVE";
  right: 22px;
  bottom: 18px;
}
.club-logo {
  width: min(32vw, 420px);
  min-width: 260px;
  position: relative;
  z-index: 1;
  filter: drop-shadow(0 22px 20px rgba(27, 24, 24, 0.2));
}
.art-rings {
  width: min(37vw, 480px);
  aspect-ratio: 1;
  position: absolute;
  border: 1px dashed var(--coral-dark);
  border-radius: 50%;
}
.art-rings::before,
.art-rings::after {
  content: "";
  position: absolute;
  inset: 7%;
  border: 1px solid currentColor;
  border-radius: 50%;
  opacity: 0.45;
}
.art-rings::after {
  inset: 19%;
  border-color: var(--coral);
}
.hero-label,
.hero-stamp {
  position: absolute;
  z-index: 2;
  display: flex;
  flex-direction: column;
  gap: 4px;
  color: var(--muted);
  font: 10px var(--mono);
  letter-spacing: 0.12em;
  text-transform: uppercase;
}
.hero-label-top {
  top: 15%;
  right: 2%;
}
.hero-label strong {
  color: var(--ink);
  font-size: 14px;
}
.hero-stamp {
  bottom: 12%;
  left: 6%;
  color: var(--coral-dark);
}
.badge-caption {
  position: absolute;
  right: 14%;
  bottom: 5%;
  left: 14%;
  z-index: 2;
  margin: 0;
  color: var(--coral-dark);
  font: 8px var(--mono);
  letter-spacing: 0.1em;
  text-align: center;
  text-transform: uppercase;
  white-space: nowrap;
}
.manifesto {
  display: grid;
  grid-template-columns: 1fr 2.6fr 1fr;
  gap: 5vw;
  position: relative;
  overflow: hidden;
  background: var(--ink);
  color: var(--paper);
}
.manifesto-index {
  display: flex;
  flex-direction: column;
  gap: 14px;
  color: #b5a59b;
  font: 10px var(--mono);
  letter-spacing: 0.14em;
}
.manifesto-index span:first-child {
  color: var(--coral);
  font-size: 22px;
}
.manifesto-body h2 {
  margin: 0 0 22px;
  font: 500 clamp(30px, 4vw, 54px)/1.25 var(--serif);
}
.manifesto-body p {
  max-width: 510px;
  color: #b7aaa1;
  font-size: 14px;
  line-height: 1.9;
}
.manifesto .text-link {
  padding: 0;
  margin-top: 16px;
  color: inherit;
}
.manifesto-side {
  align-self: end;
  color: var(--coral);
}
.manifesto-side > span {
  font:
    70px/0.4 Georgia,
    serif;
}
.manifesto-side p {
  color: var(--paper);
  font: 16px/1.7 var(--serif);
}
.event-preview {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  border-top: 1px solid var(--line);
}
.button-outline {
  margin-top: 38px;
  border-color: var(--line);
}
.button-outline:hover {
  background: var(--ink);
  color: var(--paper);
}
@media (max-width: 880px) {
  .hero {
    min-height: auto;
    grid-template-columns: 1fr;
    padding-top: 70px;
  }
  .hero-art {
    min-height: 390px;
  }
  .club-logo {
    width: min(72vw, 430px);
  }
  .art-rings {
    width: min(77vw, 470px);
  }
  .manifesto {
    grid-template-columns: 1fr 2.4fr;
  }
  .manifesto-side {
    display: none;
  }
  .event-preview {
    grid-template-columns: 1fr;
  }
}
@media (max-width: 620px) {
  .page-section,
  .hero {
    padding-right: 22px;
    padding-left: 22px;
  }
  .hero-title {
    font-size: clamp(49px, 15vw, 76px);
  }
  .hero-actions {
    flex-wrap: wrap;
    margin-bottom: 43px;
  }
  .stat-strip {
    gap: 25px;
  }
  .hero-art {
    min-height: 340px;
  }
  .badge-caption {
    right: 12%;
    bottom: 4%;
    left: 12%;
    font-size: 7px;
    letter-spacing: 0.06em;
  }
  .manifesto {
    grid-template-columns: 1fr;
    padding-top: 65px;
    padding-bottom: 65px;
  }
  .manifesto-body h2 {
    font-size: 33px;
  }
}
</style>
