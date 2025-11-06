import { createRouter, createWebHistory } from 'vue-router'
import ActiveAlarms from '../components/ActiveAlarms.vue'
import EmergencyGroupsList from '../components/EmergencyGroupsList.vue'
import CreateNewGroup from '../components/CreateNewGroup.vue'
import EmergencyGroupForm from '../components/EmergencyGroupForm.vue'
import ActivateAlarm from '../components/ActivateAlarm.vue'

const routes = [
  {
    path: '/',
    redirect: '/active-alarms'
  },
  {
    path: '/active-alarms',
    name: 'ActiveAlarms',
    component: ActiveAlarms
  },
  {
    path: '/emergency-groups',
    name: 'EmergencyGroupsList',
    component: EmergencyGroupsList
  },
  {
    path: '/emergency-groups/new',
    name: 'CreateNewGroup',
    component: CreateNewGroup
  },
  {
    path: '/emergency-groups/:id',
    name: 'EmergencyGroupDetail',
    component: EmergencyGroupForm
  },
  {
    path: '/emergency-groups/:id/activate',
    name: 'ActivateAlarm',
    component: ActivateAlarm
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
