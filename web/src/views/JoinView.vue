<script setup>
import { ref } from "vue";
import Eyebrow from "../components/Eyebrow.vue";
import joinPoster from "../../image/qq.jpg";

const submitted = ref(false);
function submit() {
  submitted.value = true;
}
</script>

<template>
  <main class="view view-inner">
    <section class="inner-hero join-hero">
      <div class="inner-hero-copy">
        <Eyebrow label="COME AS YOU ARE" />
        <h1>加入<br />微光漫摄喵</h1>
        <p>不需要“会什么”，只要你真的喜欢。</p>
      </div>
      <div class="inner-hero-number"><span>05</span><span>OPEN CALL</span></div>
    </section>
    <section class="join-grid page-section">
      <div class="join-copy">
        <h2>下一帧，<br />等你来按下。</h2>
        <p>加入漫摄喵，加入漫摄影谢谢喵。</p>
        <div class="join-note">
          <span>OPEN</span><span>常年招新 · 新人友好</span>
        </div>
      </div>
      <figure class="join-visual">
        <img :src="joinPoster" alt="微光漫摄 QQ 群二维码海报" />
        <figcaption>扫码加入群聊，和同频的人在线见面。</figcaption>
      </figure>
    </section>
  </main>
</template>

<style scoped>
.view-inner {
  min-height: calc(100dvh - var(--header-height) - 34px);
}
.page-section {
  padding: clamp(64px, 8vw, 110px) clamp(24px, 9vw, 150px);
}
.inner-hero {
  min-height: 430px;
  padding: 84px clamp(24px, 9vw, 150px);
  display: flex;
  align-items: end;
  justify-content: space-between;
  position: relative;
  overflow: hidden;
  background: var(--panel);
  color: #f4eee5;
  border-bottom: 1px solid rgba(244, 238, 229, 0.16);
  clip-path: polygon(0 0, 97% 0, 100% 10%, 100% 100%, 3% 100%, 0 91%);
}
.inner-hero::before {
  content: "";
  position: absolute;
  inset: 22px;
  border: 1px solid rgba(244, 238, 229, 0.1);
  pointer-events: none;
}
.inner-hero::after {
  content: "";
  width: 170px;
  height: 1px;
  position: absolute;
  top: 64px;
  right: 8%;
  background: var(--cyan);
  box-shadow:
    0 9px 0 rgba(112, 214, 208, 0.35),
    0 18px 0 rgba(112, 214, 208, 0.15);
}
.inner-hero-copy,
.inner-hero-number {
  position: relative;
  z-index: 1;
}
.inner-hero-copy {
  max-width: 670px;
}
.inner-hero h1 {
  margin: 0;
  white-space: pre-line;
  font: 600 clamp(55px, 8vw, 104px)/0.98 var(--serif);
  letter-spacing: -0.03em;
  text-shadow: 4px 4px 0 rgba(196, 82, 78, 0.22);
}
.inner-hero p {
  margin: 26px 0 0;
  color: #b9aaa2;
  font-size: 14px;
}
.inner-hero .eyebrow {
  color: #e17a70;
}
.inner-hero-copy .eyebrow::after {
  content: "";
  width: 46px;
  height: 1px;
  margin-left: 8px;
  background: var(--cyan);
}
.inner-hero-copy h1::after {
  content: "/";
  display: inline-block;
  margin-left: 12px;
  color: var(--cyan);
  font: 400 0.45em var(--mono);
  vertical-align: top;
  transform: translateY(12px);
}
.inner-hero-number {
  align-self: start;
  display: flex;
  flex-direction: column;
  gap: 8px;
  color: #a5958b;
  font: 10px var(--mono);
  letter-spacing: 0.12em;
  text-transform: uppercase;
}
.inner-hero-number span:first-child {
  color: var(--coral);
  font: 34px var(--serif);
}
.inner-hero-number span:last-child {
  color: var(--cyan);
}
.join-hero {
  background: var(--coral-dark);
}
.join-hero .eyebrow {
  color: #f4c5b2;
}
.join-hero p {
  color: #f0cfc0;
}
.join-grid {
  max-width: 1200px;
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 12vw;
  position: relative;
}
.join-grid::before {
  content: "DATA // NO EXTERNAL FEED";
  position: absolute;
  top: 30px;
  right: clamp(24px, 9vw, 150px);
  color: var(--muted);
  font: 8px var(--mono);
  letter-spacing: 0.1em;
}
.join-copy h2 {
  margin: 0 0 24px;
  font: 500 clamp(30px, 4vw, 50px)/1.2 var(--serif);
  text-shadow: 3px 3px 0 rgba(196, 82, 78, 0.1);
}
.join-copy > p {
  max-width: 460px;
  margin: 0;
  color: var(--muted);
  font-size: 14px;
  line-height: 1.9;
}
.join-note {
  margin-top: 44px;
  display: flex;
  flex-direction: column;
  gap: 8px;
  color: var(--coral-dark);
  font: 10px var(--mono);
  letter-spacing: 0.12em;
  text-transform: uppercase;
}
.join-note span {
  color: var(--ink);
  font-size: 9px;
}
.join-visual {
  width: min(100%, 420px);
  margin: 0;
  padding: clamp(10px, 1.4vw, 16px);
  position: relative;
  justify-self: end;
  overflow: hidden;
  background: #111113;
  border: 1px solid var(--cyan);
  box-shadow: 12px 12px 0 var(--coral-dark);
  clip-path: polygon(0 0, 96% 0, 100% 4%, 100% 100%, 4% 100%, 0 96%);
}
.join-visual::before,
.join-visual::after {
  position: absolute;
  z-index: 2;
  font: 8px var(--mono);
  letter-spacing: 0.12em;
}
.join-visual::before {
  content: "JOIN / 2026";
  top: 24px;
  right: 24px;
  color: rgba(255, 255, 255, 0.62);
}
.join-visual::after {
  content: "SCAN / CONNECT";
  right: 22px;
  bottom: 52px;
  color: var(--cyan);
}
.join-visual img {
  width: 100%;
  height: auto;
  aspect-ratio: 0.6;
  display: block;
  object-fit: contain;
}
.join-visual figcaption {
  margin: 13px 4px 2px;
  color: rgba(255, 255, 255, 0.68);
  font-size: 11px;
  line-height: 1.6;
}
@media (max-width: 880px) {
  .inner-hero {
    clip-path: polygon(0 0, 96% 0, 100% 5%, 100% 100%, 4% 100%, 0 95%);
  }
  .join-grid {
    gap: 56px;
  }
}
@media (max-width: 620px) {
  .page-section,
  .inner-hero {
    padding-right: 22px;
    padding-left: 22px;
  }
  .inner-hero {
    min-height: 330px;
    padding-top: 70px;
    padding-bottom: 55px;
  }
  .inner-hero h1 {
    font-size: clamp(49px, 15vw, 76px);
  }
  .inner-hero-number {
    display: none;
  }
  .join-grid {
    grid-template-columns: 1fr;
    gap: 52px;
  }
  .join-visual {
    width: min(100%, 430px);
    margin: 0 auto;
    justify-self: stretch;
    box-shadow: 8px 8px 0 var(--coral-dark);
  }
}
</style>
