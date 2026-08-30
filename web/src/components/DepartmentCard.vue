<script setup>
defineProps({
  department: {
    type: Object,
    required: true,
  },
  index: {
    type: Number,
    required: true,
  },
});
</script>

<template>
  <article class="department-card">
    <header class="department-card-header">
      <span>UNIT / {{ String(index + 1).padStart(2, "0") }}</span>
      <span class="department-status"><i></i>ACTIVE</span>
    </header>

    <div class="department-card-body">
      <div class="department-index" aria-hidden="true">
        <span>{{ String(index + 1).padStart(2, "0") }}</span>
        <b></b>
      </div>
      <div class="department-copy">
        <p class="department-code">{{ department.code }}</p>
        <h3>{{ department.name }}</h3>
        <p class="department-intro">{{ department.intro }}</p>
      </div>
      <span class="department-arrow" aria-hidden="true">↗</span>
    </div>

    <footer class="department-card-footer">
      <span>ARCHIVE ENTRY</span>
      <span>{{ String(index + 1).padStart(2, "0") }} / 05</span>
    </footer>
  </article>
</template>

<style scoped>
.department-card {
  min-height: 232px;
  padding: 18px 20px 15px;
  position: relative;
  display: flex;
  flex-direction: column;
  overflow: hidden;
  border: 1px solid var(--line);
  background: color-mix(in srgb, var(--paper) 86%, var(--cyan) 14%);
  transition:
    transform 0.28s ease,
    border-color 0.28s ease,
    background-color 0.28s ease;
}
.department-card::before {
  content: "";
  width: 84px;
  height: 84px;
  position: absolute;
  top: -42px;
  right: -28px;
  border: 1px solid rgba(112, 214, 208, 0.44);
  border-radius: 50%;
  box-shadow: 0 0 0 10px rgba(112, 214, 208, 0.06);
  pointer-events: none;
}
.department-card::after {
  content: "";
  width: 42%;
  height: 1px;
  position: absolute;
  right: 20px;
  bottom: 45px;
  background: var(--coral);
  opacity: 0.5;
  pointer-events: none;
}
.department-card:hover {
  transform: translateY(-5px);
  border-color: var(--coral);
  background: color-mix(in srgb, var(--paper) 80%, var(--coral) 20%);
}
.department-card-header,
.department-card-footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
  color: var(--muted);
  font: 9px var(--mono);
  letter-spacing: 0.12em;
}
.department-card-header {
  padding-bottom: 13px;
  border-bottom: 1px solid var(--line);
}
.department-status {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  color: var(--coral-dark);
}
.department-status i {
  width: 5px;
  height: 5px;
  display: inline-block;
  border-radius: 50%;
  background: var(--cyan);
  box-shadow: 0 0 0 3px rgba(112, 214, 208, 0.16);
}
.department-card-body {
  min-height: 145px;
  padding: 23px 0 18px;
  display: grid;
  grid-template-columns: 52px minmax(0, 1fr) auto;
  align-items: start;
  gap: 16px;
  position: relative;
  z-index: 1;
}
.department-index {
  display: flex;
  flex-direction: column;
  gap: 12px;
  color: var(--coral);
  font: 500 28px/1 var(--mono);
}
.department-index b {
  width: 30px;
  height: 4px;
  display: block;
  background: var(--coral);
  box-shadow:
    9px 0 0 rgba(196, 82, 78, 0.36),
    18px 0 0 rgba(196, 82, 78, 0.16);
}
.department-copy {
  min-width: 0;
}
.department-code {
  margin: 0 0 8px;
  color: var(--coral-dark);
  font: 9px var(--mono);
  letter-spacing: 0.16em;
}
.department-copy h3 {
  margin: 0;
  color: var(--ink);
  font: 600 clamp(22px, 2.5vw, 32px)/1.15 var(--serif);
  text-wrap: balance;
}
.department-intro {
  max-width: 34em;
  margin: 13px 0 0;
  color: var(--muted);
  font-size: 12px;
  line-height: 1.75;
}
.department-arrow {
  padding-top: 2px;
  color: var(--coral-dark);
  font: 20px/1 var(--mono);
  transition: transform 0.28s ease;
}
.department-card:hover .department-arrow {
  transform: translate(3px, -3px);
}
.department-card-footer {
  margin-top: auto;
  padding-top: 12px;
  border-top: 1px solid var(--line);
  color: var(--muted);
}
.department-card-footer span:last-child {
  color: var(--coral-dark);
}
@media (max-width: 620px) {
  .department-card {
    min-height: 218px;
    padding: 16px 16px 14px;
  }
  .department-card-body {
    grid-template-columns: 42px minmax(0, 1fr) auto;
    gap: 12px;
  }
  .department-index {
    font-size: 23px;
  }
  .department-copy h3 {
    font-size: 24px;
  }
  .department-intro {
    font-size: 12px;
  }
}
</style>
