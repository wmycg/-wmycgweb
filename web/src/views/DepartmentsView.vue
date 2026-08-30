<script setup>
import { computed } from "vue";
import { departments } from "../data/departments.js";
import DepartmentCard from "../components/DepartmentCard.vue";
import Eyebrow from "../components/Eyebrow.vue";

const visibleDepartments = computed(() =>
  departments.filter((department) => department?.name && department?.intro),
);
</script>

<template>
  <main class="view view-inner">
    <section class="inner-hero works-hero">
      <div class="inner-hero-copy">
        <Eyebrow label="MEET THE DEPARTMENTS" />
        <h1>
          部门<br />
          分类
        </h1>
        <p>五个创作单元，各自负责一束微光，也一起完成一部作品。</p>
      </div>
      <div class="inner-hero-number">
        <span>03</span>
        <span>UNITS ONLINE</span>
      </div>
    </section>

    <section class="department-list page-section">
      <div class="list-heading">
        <div>
          <span class="list-kicker">UNIT ROSTER / 2026</span>
          <h2>选择你的作战位置。</h2>
        </div>
        <p>每个部门都从兴趣出发，向一件完整作品推进。</p>
      </div>

      <TransitionGroup name="department-list" tag="div" class="department-grid">
        <DepartmentCard
          v-for="(department, index) in visibleDepartments"
          :key="department.id"
          :department="department"
          :index="index"
        />
      </TransitionGroup>
      <div v-if="!visibleDepartments.length" class="department-empty">
        <span>NO UNIT DATA</span>
        <p>部门资料正在整理中。</p>
      </div>
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
.department-list {
  padding-top: 72px;
  position: relative;
}
.department-list::before {
  content: "DATA // INTERNAL ARCHIVE";
  position: absolute;
  top: 30px;
  right: clamp(24px, 9vw, 150px);
  color: var(--muted);
  font: 8px var(--mono);
  letter-spacing: 0.1em;
}
.list-heading {
  margin-bottom: 42px;
  display: flex;
  align-items: end;
  justify-content: space-between;
  gap: 30px;
  border-left: 3px solid var(--coral);
  padding-left: 17px;
}
.list-kicker {
  color: var(--muted);
  font: 9px var(--mono);
  letter-spacing: 0.14em;
  text-transform: uppercase;
}
.list-heading h2 {
  margin: 10px 0 0;
  color: var(--ink);
  font: 500 clamp(28px, 4vw, 48px)/1.2 var(--serif);
  text-wrap: balance;
}
.list-heading > p {
  max-width: 260px;
  margin: 0 0 2px;
  color: var(--muted);
  font-size: 12px;
  line-height: 1.75;
}
.department-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 18px;
}
.department-list-enter-active,
.department-list-leave-active {
  transition:
    opacity 0.25s ease,
    transform 0.25s ease;
}
.department-list-enter-from,
.department-list-leave-to {
  opacity: 0;
  transform: translateY(12px);
}
.department-card:nth-child(3n) {
  grid-column: span 2;
}
.department-empty {
  padding: 52px 20px;
  border: 1px dashed var(--line-strong);
  color: var(--muted);
  text-align: center;
}
.department-empty span {
  color: var(--coral-dark);
  font: 10px var(--mono);
  letter-spacing: 0.14em;
}
.department-empty p {
  margin: 12px 0 0;
  font-size: 13px;
}
@media (max-width: 880px) {
  .inner-hero {
    clip-path: polygon(0 0, 96% 0, 100% 5%, 100% 100%, 4% 100%, 0 95%);
  }
  .department-grid {
    gap: 16px;
  }
  .department-card:nth-child(3n) {
    grid-column: auto;
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
  .list-heading {
    align-items: start;
    flex-direction: column;
    gap: 16px;
  }
  .list-heading > p {
    max-width: 30em;
  }
  .department-grid {
    grid-template-columns: 1fr;
    gap: 14px;
  }
}
</style>
