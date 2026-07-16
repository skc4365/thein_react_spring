
import Nav from '../components/Nav'
import { Outlet } from 'react-router-dom'
import Header from '../components/Header'
import Foooter from '../components/Foooter'

export default function Layout() {
  return (
    <div className="container">
      <Header />
      <Nav />
      <main className="main">
        
        <Outlet /> {/* Route가 바뀌는 영역 */}
      </main>
      <Foooter />
    </div>
  )
}
