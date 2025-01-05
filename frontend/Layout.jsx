import React from 'react'
import Navbar from './src/components/Navabr/Navbar'
import { Outlet } from 'react-router-dom'
import { Footer } from './src/components/Footer/Footer'

export const Layout = () => {
  return (
   <>
    {/* <Navbar/> */}
    <Outlet/>
    <Footer/>
   </>
  )
}
