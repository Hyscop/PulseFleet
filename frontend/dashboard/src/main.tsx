import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import "./index.css";
import App from "./App.tsx";
import { DeviceList } from "./components/DeviceList";
import { DeviceDetail } from "./components/DeviceDetail";

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<App />}>
          <Route index element={<DeviceList />} />
          <Route path="devices/:id" element={<DeviceDetail />} />
        </Route>
      </Routes>
    </BrowserRouter>
  </StrictMode>
);
