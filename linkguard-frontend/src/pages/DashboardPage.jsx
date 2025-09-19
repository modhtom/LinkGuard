import React, { useState, useEffect } from "react";
import UrlForm from "../components/UrlForm";
import UrlResultCard from "../components/UrlResultCard";
import TrendingDomains from "../pages/TrendingDomains";
import { expandUrl } from "../services/urlService";
import { getTrendingDomains } from "../services/analyticsService";
import styles from "./DashboardPage.module.css";

const DashboardPage = () => {
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState("");
  const [result, setResult] = useState(null);

  const [trends, setTrends] = useState([]);
  const [trendsLoading, setTrendsLoading] = useState(true);
  const [trendsError, setTrendsError] = useState("");

  const fetchTrends = async () => {
    setTrendsLoading(true);
    setTrendsError("");
    try {
      const response = await getTrendingDomains();
      setTrends(response.data);
    } catch (err) {
      setTrendsError("Could not load trending domains.");
      console.error(err);
    } finally {
      setTrendsLoading(false);
    }
  };

  useEffect(() => {
    fetchTrends();
  }, []);

  const handleExpandUrl = async (url) => {
    setIsLoading(true);
    setError("");
    setResult(null);
    try {
      const response = await expandUrl(url);
      setResult(response.data);
      // After a successful expansion, refresh the trends
      fetchTrends();
    } catch (err) {
      setError("Failed to expand URL. It might be invalid or unreachable.");
      console.error(err);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className={styles.dashboard}>
      <div className={styles.mainContent}>
        <h2>URL Expander</h2>
        <UrlForm onExpand={handleExpandUrl} isLoading={isLoading} />
        {error && <p className={styles.error}>{error}</p>}
        <UrlResultCard result={result} />
      </div>
      <div className={styles.sidebar}>
        <TrendingDomains
          domains={trends}
          isLoading={trendsLoading}
          error={trendsError}
        />
      </div>
    </div>
  );
};

export default DashboardPage;
