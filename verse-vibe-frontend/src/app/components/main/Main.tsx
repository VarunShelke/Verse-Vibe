'use client';

import { get } from '@/app/axios/axios';
import { Song } from '@/app/models/models';
import { Box } from '@mui/material';
import { HttpStatusCode } from 'axios';
import { useState } from 'react';
import toast from 'react-hot-toast';
import SearchResults from '../search-results/SearchResults';
import SearchBox from '../search/SearchBox';
import styles from './Main.module.css';

export default function Main() {
  const [searchPerfomed, setSearchPerformed] = useState<boolean>(false);
  const [searchQuery, setSearchQuery] = useState<string>('');
  const [searchResults, setSearchResults] = useState<Song[]>([]);

  const handleSearchQueryInput = (event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    setSearchQuery(event.target.value);
  };

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const handleSearch = async (event: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    if (searchQuery.trim() === '') {
      return;
    }

    const response = await get('/search', { searchQuery: searchQuery });
    if (response?.status === HttpStatusCode.Ok) {
      setSearchResults(response.data);
      setSearchPerformed(true);
      toast.success('Search Successful!');
    }
  };

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const handleClearResults = async (event: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    setSearchResults([]);
    setSearchPerformed(false);
  };

  return (
    <Box className={styles['main-content']}>
      <SearchBox
        value={searchQuery}
        onChange={handleSearchQueryInput}
        search={handleSearch}
        clearResults={handleClearResults}
      />
      {searchPerfomed && <SearchResults results={searchResults} />}
    </Box>
  );
}
