import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Layout from './route/Layout';
import Home from './pages/home/Home';
import Profile from './pages/profile/Profile';
import BoardGames from './pages/boardgames/BoardGames';
import Contact from './pages/contact/Contact';
import NoPage from "./route/NoPage"

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Home />} />
          <Route path="profile" element={<Profile />} />
          <Route path="boardgames" element={<BoardGames />} />
          <Route path="contact" element={<Contact />} />
          <Route path="*" element={<NoPage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
